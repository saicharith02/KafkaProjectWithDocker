from json import dumps
import os
import concurrent.futures
import logging
from bs4 import BeautifulSoup
from kafka import KafkaProducer
import spacy
import warnings

warnings.filterwarnings("ignore", category=FutureWarning, module='huggingface_hub.file_download')

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Load spaCy model for NER
nlp = spacy.load("en_core_web_sm")

# Initialize Kafka producer
kafka_servers = os.getenv("KAFKA_BOOTSTRAP_SERVERS", "localhost:9092")
kafka_topic = os.getenv("KAFKA_TOPIC", "wordcollector1")
my_producer = KafkaProducer(
    bootstrap_servers=[kafka_servers],
    value_serializer=lambda v: dumps(v).encode('utf-8')
)

def read_file(file_path):
    try:
        with open(file_path, 'r', encoding="utf-8") as file:
            data = file.read()
        soup = BeautifulSoup(data, 'html.parser')
        return soup.get_text()
    except Exception as e:
        logger.error(f"Error reading {file_path}: {e}")
        return None

def process_files(file_paths):
    for file_path in file_paths:
        try:
            data = read_file(file_path)
            if data:
                doc = nlp(data)
                entities = [(ent.text, ent.label_) for ent in doc.ents]
                logger.info(f"Entities: {entities}")
                my_producer.send(kafka_topic, value={"entities": entities})
        
        except Exception as exc:
            logger.error(f"{file_path} generated an exception: {exc}")

file_paths = []
with open("links.txt", "r") as fp:
    for line in fp:
        file_paths.append(line.strip())

with concurrent.futures.ThreadPoolExecutor(max_workers=8) as executor:
    executor.map(process_files, [file_paths])

my_producer.flush()
my_producer.close()
logger.info("Data has been forwarded to the consumer")

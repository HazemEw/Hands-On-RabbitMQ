import pika
import json
import logging
from flask import Flask, jsonify
from faker import Faker
import random

app = Flask(__name__)
fake = Faker()

logging.basicConfig(level=logging.INFO)

def send_log_to_rabbitmq(log_message, severity):
    try:
        connection_params = pika.ConnectionParameters(host='localhost')
        connection = pika.BlockingConnection(connection_params)
        channel = connection.channel()


        log = {'log_message': log_message}

        channel.basic_publish(
            exchange='logs_exchange',
            routing_key=severity,
            body=json.dumps(log)
        )

        logging.info(f"Sent {severity.upper()} log: {log_message}")
    except Exception as e:
        logging.error(f"Error sending log: {str(e)}")
    finally:
        if connection:
            connection.close()

@app.route('/api/logs/generate', methods=['POST'])
def generate_logs():
    severities = ['info', 'error', 'warning']
    for _ in range(10):
        log_message = fake.sentence()
        severity = random.choice(severities)
        send_log_to_rabbitmq(log_message, severity)
    return jsonify({"status": "success", "message": "10 random logs sent to RabbitMQ."}), 200

if __name__ == "__main__":
    app.run(debug=True)

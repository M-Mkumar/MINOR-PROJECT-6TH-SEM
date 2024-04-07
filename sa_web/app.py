# http://127.0.0.1:5000

from flask import Flask, render_template, request

import pickle
from sklearn.feature_extraction.text import TfidfVectorizer

app = Flask(__name__)

# Load your trained model
with open("sentiment_analysis.pkl", "rb") as f:
    model = pickle.load(f)

# Load the TF-IDF vectorizer used during training
with open("tfidf_vectorizer.pkl", "rb") as f:
    tfidf_vectorizer = pickle.load(f)


# Define a route to render the home page with the input form
# @app.route("/")
# def home():
#     return render_template("index.html")


# Define a route to handle form submission and make predictions
@app.route("/predict", methods=["POST"])
def predict():
    # Get input text from the form
    input_text = request.form["tweet"]

    # Preprocess the input text using TF-IDF vectorization
    input_vector = tfidf_vectorizer.transform([input_text])

    # Make predictions using your model
    prediction = model.predict(input_vector)

    # Return the prediction to the user
    return str(prediction[0])


if __name__ == "__main__":
    app.run(debug=False, host = '0.0.0.0')

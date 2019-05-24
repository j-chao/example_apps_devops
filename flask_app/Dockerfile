# Inherit and use a base Python 3 image
FROM python:alpine3.9

# It's always good practice to label your image with 
# some metadata and contact information, for others to reference
LABEL maintainer some-random-TDP

# Copy the contents of the current working directory to 
# the /app directory of the container machine
COPY . /app

# Change the working directory to the /app directory
WORKDIR /app

# Install python dependencies
RUN pip install -r requirements.txt

# Expose port 5000 on the container
EXPOSE 5000

# Run the python application 
ENTRYPOINT ["python", "app.py"]


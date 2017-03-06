FROM python:3.4
ENV PYTHONBUFFERED 1
RUN mkdir /src
ADD requirements.txt /src/
RUN pip install -r /src/requirements.txt
WORKDIR /src

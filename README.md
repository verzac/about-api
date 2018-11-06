# Build Status
[![Build Status](https://travis-ci.org/verzac/about-api.svg?branch=master)](https://travis-ci.org/verzac/about-api)

# About API
This project is designed to be the backend of the [about.benjamintanone.com](https://github.com/verzac/about.benjamintanone.com) project.

## To Run
1. Fill out the necessary information in application-local.yml
2. Deploy redis locally (can be done through Docker: `docker run --name about-api -p 6379:6379 -d redis`)
3. Execute the following code
```
./gradlew bootRun
```

## Features
* Allows the delayed sending of Contact Form submissions. That is, contact form submissions are temporarily stored in Redis and the user is required to respond to a "challenge" before it is sent.
* Dispatches emails containing contact forms asynchronously (as email dispatch can take up to 17 seconds).
* Uses an external SMTP service (I used it with GMail).

## Architecture
* Uses Spring Boot 2 with Redis as its data storage.
* Deployed to [Elastic Beanstalk](https://aws.amazon.com/elasticbeanstalk/) using [Travis](https://travis-ci.org) as a CI/CD tool.
<img src="https://cdn.prod.website-files.com/677c400686e724409a5a7409/6790ad949cf622dc8dcd9fe4_nextwork-logo-leather.svg" alt="NextWork" width="300" />

# Dockerize a Spring Boot URL Shortener

**Project Link:** [View Project](https://learn.nextwork.org/projects/5aa6f0f9-cd65-4505-9d54-0de0d1a5ba0b)

**Author:** Dhruv Patel  
**Email:** dpatel5469@gmail.com

---

![Image](https://learn.nextwork.org/relaxed_silver_timid_hind/uploads/5aa6f0f9-cd65-4505-9d54-0de0d1a5ba0b_51egjbfy)

## Project Overview

### Goals and motivation

I'm building a URL Shortener REST API to learn docker

## Building the URL Shortener REST API

### API design approach

In this step, I'm building JPA entity and repository for storing hortened URLs, implement a service that generates random Base62 short codes, and a REST controller so that the API can create, resolve, and list Shortened URLs.

![Image](https://learn.nextwork.org/relaxed_silver_timid_hind/uploads/5aa6f0f9-cd65-4505-9d54-0de0d1a5ba0b_wutn1mpx)

### Base62 encoding and randomness

Base62 encoding generatesgenerates a unique code length `CODE_LENGTH` in our case 7. Randomness matters because if we use sequential ordering anyone can guess next unique code.

## Containerizing with a Multi-Stage Dockerfile and Docker Compose

### Containerization strategy

In this step, I'm containerizing my URL Shortener with PostgreSQL database together so that I can deploy them.

![Image](https://learn.nextwork.org/relaxed_silver_timid_hind/uploads/5aa6f0f9-cd65-4505-9d54-0de0d1a5ba0b_f40hdnd5)

### Testing URL shortening locally

I shortened the url to the docker's get started with docker page and when I visited the short code, the browser redirected me to the docker's get started with docker page.

## Connecting to Neon PostgreSQL for Production

### Production database setup

In this step, I'm setting up Neon PostgreSQL database so that I can deploy my app on cloud and my app can reach database from anywhere.

![Image](https://learn.nextwork.org/relaxed_silver_timid_hind/uploads/5aa6f0f9-cd65-4505-9d54-0de0d1a5ba0b_wg11hmzb)

### JDBC URL configuration for Neon

I used jdbc:postgresql://host/database?sslmode=require because Spring Boot requires that we do not add database's USERNAME and DATABASE_PASSWORD to the main url.

## Deploying a Live Public API on Render

### Deployment process

In this step, I'm deploying my app to Render so that I can share my live project link in my resume.

![Image](https://learn.nextwork.org/relaxed_silver_timid_hind/uploads/5aa6f0f9-cd65-4505-9d54-0de0d1a5ba0b_51egjbfy)

### Live URL and verification

My live URL is https://url-shortener-using-rest-api.onrender.com/swagger-ui/index.html. I tested it by shortening a URL and then visiting the short code, which redirected me to my GitHub repository for this project at https://github.com/Dhruv0306/URL-Shortener-Using-REST-API.

## Adding Click Analytics Tracking

![Image](https://learn.nextwork.org/relaxed_silver_timid_hind/uploads/5aa6f0f9-cd65-4505-9d54-0de0d1a5ba0b_qw8dl8yq)

### Capturing visitor metadata on redirect

In this project extension, my redirect endpoint extracts the  time stamp, referrer, user_agent from headers to record click envents. This allows us to monitor how many people has used the shortened url.

## Reflections and Takeaways

### Key tools and concepts learned

The key tools I used include Docker, GitHub, Git, Java, Spring Boot Framework. Key concepts I learnt include How to build REST APIs, How to use Docker to build an image and use docker to host sites on Render.

### Time and challenges

This project took me approximately 2 Hours. The most challenging part was to get Docker Up and Runnig and building first image.

I did this project today to learn how to use Docker to host project, learning how url Shortner works, and how REST APIs works. Another skill I want to learn is Advance Docker.

---

*Built with [NextWork](https://learn.nextwork.org) - [View this project](https://learn.nextwork.org/projects/5aa6f0f9-cd65-4505-9d54-0de0d1a5ba0b)*

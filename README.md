# autograding-server
I'm going to try to use Redis, Docker, API Gateways, and more to implement a scalable auto-grading system for programming competitions. Check back to monitor my progress.

## Design
It's a little half-baked and a few more things need to be thought through, but I could get started on it with the existing stuff in place and make changes.
<img width="2623" height="1341" alt="image" src="https://github.com/user-attachments/assets/d42a6752-74a0-4f13-a5bc-5c81d8eeb60f" />

## Phase 1: Hello World (1-2 Days)
-	Setup Redis and Postgres (locally or Docker).	
-	Gateway: Write a simple API that accepts a file upload and pushes a JSON message to Redis.
-	Worker: Write a script that listens to Redis, prints "I got a job!", and saves "Done" to Postgres.
-	Result: No real grading, but the pipes are connected.

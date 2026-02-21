# autograding-server
I'm going to try to use Redis, Docker, API Gateways, and more to implement a scalable auto-grading system for programming competitions. Check back to monitor my progress.

## Design
It's a little half-baked and a few more things need to be thought through, but I could get started on it with the existing stuff in place and make changes.
<img width="2623" height="1341" alt="image" src="https://github.com/user-attachments/assets/d42a6752-74a0-4f13-a5bc-5c81d8eeb60f" />

Some optimizations I can make include token bucketing for rate limits and not reinitializing the docker container for every testcase. There is support for adding other languages like python and java by configuring more workers.

You can change the testcases in TestCaseSchema.json

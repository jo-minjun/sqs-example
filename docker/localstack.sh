#!/bin/bash
/usr/local/bin/awslocal sqs create-queue --queue-name local-example
/usr/local/bin/awslocal sqs list-queues

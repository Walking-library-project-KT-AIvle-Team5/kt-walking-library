# 

## Model
www.msaez.io/#/102575038/storming/a19d7185511e9734d6d2506f14db0b52

## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- auth
- dashboard
- bookmanagement
- pointmanagement
- subscribermanagement
- libraryplatform
- authormanagement


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- auth
```
 http :8088/memberManagements id="id"isKtCustomer="isKtCustomer"loginId="loginId"password="password"name="name"role="role"status="status"
```
- dashboard
```
```
- bookmanagement
```
 http :8088/books bookId="bookId"bookName="bookName"authorId="authorId"authorName="authorName"category="category"summary="summary"imagePath="imagePath"contents="contents"publishedAt="publishedAt"isBestseller="isBestseller"
 http :8088/manuscripts manuscriptId="manuscriptId"title="title"authorId=" authorId"content="content"imageRequested="imageRequested"aiSummaryRequested="aiSummaryRequested"category="category"imagePath="imagePath"summary="summary"price="price"
```
- pointmanagement
```
 http :8088/points pointAccountId="pointAccountId"userId="userId"currentPoint="currentPoint"totalCharged="totalCharged"totalUsed="totalUsed"isktCustomer="isktCustomer"
```
- subscribermanagement
```
 http :8088/subscribeManagements subscriptionId="subscriptionId"userId="userId"status="status"startedAt="startedAt"endsAt="endsAt"lastRenewalAt="lastRenewalAt"
 http :8088/readingManagements readingActivityId="readingActivityId"userId="userId"bookId="bookId"currentPage="currentPage"readingStatus="readingStatus"startedAt="startedAt"completedAt="completedAt"lastUpdatedAt="lastUpdatedAt"
 http :8088/pointRequestManagements pointRequestId="pointRequestId"userId="userId"requestedPointAmount="requestedPointAmount"paymentMethodId="paymentMethodId"status="status"timestamp="timestamp"
 http :8088/bookPurchaseManagements purchaseRequestId="purchaseRequestId"userId="userId"bookId="bookId"
```
- libraryplatform
```
 http :8088/bookPublications id="id"title=" title"summaryContent=" summaryContent"summary=" summary"category=" category"imagepath=" imagepath"contents=" contents"status="status"price="price"
 http :8088/platformManagements id="id"subscribedUserIds="subscribedUserIds"recommendationMessage="recommendationMessage"
```
- authormanagement
```
 http :8088/authorRegistrations authorId="authorId"authorName="authorName"authorInfo="authorInfo"authorPortfolio="authorPortfolio"authorRole="authorRole"craeteDate="craeteDate"
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```

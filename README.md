# CrowwwdSync

CrowwwdSync is a ticketing web application tailored for concert-goers, prioritising a hassle-free and enjoyable experience for users purchasing tickets. 
### Currently Available Features
1. Account-Access for a seamless experience
    - Users can easily create an account and login to CrowwwdSync. The account keeps track of purchases and other personalised features of our platform.
2. Event Viewing
    - Users can explore and anticipate upcoming events. CrowwwdSync’s modern design and comprehensive view of upcoming concerts encourage users to interact with and purchase tickets.
3. Intuitive Ticketing Process
    - Users can purchase tickets for a concert they want to attend. With our process designed to reduce confusion, users can have a hassle-free enjoyable experience purchasing tickets.

## Built With
[![Next][Next.js]][Next-url]
[![TailwindCSS][TailwindCSS]][TailwindCSS-url]
[![Axios][Axios]][Axios-url]
[![SpringBoot][SpringBoot]][SpringBoot-url]
[![Mongo][MongoDB]][Mongo-url]
[![AmazonS3][AmazonS3]][AmazonS3-url]

Our tech stack includes 
- Next.js, Tailwind CSS and Axios for our frontend framework
- Spring Boot for our backend framework
- MongoDB and Amazon Web Services S3 for our data infrastructure

## Getting Started

### Prerequisites
Since our Spring Boot application is built on Apache Maven, Maven will have to be installed.  
You can refer [here](https://maven.apache.org/install.html) for installation instructions. 

---
### Installation and Usage

#### `Step 1` - Clone the repo
```
bash
$ git clone https://github.com/swiifttay/crowwwd.git
```
#### `Step 2` - cd into frontend
```
bash
$ cd frontend
```
#### `Step 3` - Install dependencies for frontend
```
bash
$ npm i
```
#### `Step 4` - Run frontend
```
bash
$ npm run dev
```
#### `Step 5` - cd into backend
```
bash
$ cd ../backend
```
#### `Step 6` - create secrets.properties file in in `backend/src/main/resources`
```
spring.data.mongodb.database=<your-database>
spring.data.mongodb.uri=<your-database-uri>

aws.bucket.region=<your-aws-bucket-region>
aws.bucket.name=<your-aws-bucket-name>
aws.access.key=<your-aws-access-key>
aws.secret.key=<your-aws-secret-key>

spotify.client.id=<your-spotify-client-id>
spotify.client.secretkey=<your-spotify-secret-key>

crowwwd.backend.app.jwtSecretKey=<your-jwt-secret-key>
```
#### `Step 7` - Run backend
```
bash
$ ./mvnw spring-boot:run
```
Enter browser and open http://localhost:3000

## Contributors

**Frontend:**
- Dexter    [(dexterlim24)](https://github.com/dexterlim24)
- Yuting    [(yutinggh)](https://github.com/yutinggh)
- Joel      [(owjoel)](https://github.com/owjoel)

**Backend:**
- Eugene    [(eugenelian)](https://github.com/eugenelian)
- Si Yu     [(swiifttay)](https://github.com/swiifttay)
- Jeremiah  [(jtrabino)](https://github.com/jtrabino)

<!-- MARKDOWN LINKS & IMAGES -->
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[TailwindCSS]: https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white
[TailwindCSS-url]: https://tailwindcss.com
[Axios]: https://img.shields.io/badge/axios-5a29e4?style=for-the-badge&logo=axios
[Axios-url]: https://axios-http.com
[SpringBoot]: https://img.shields.io/badge/spring%20boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white
[SpringBoot-url]: https://spring.io/projects/spring-boot
[MongoDB]: https://img.shields.io/badge/MongoDB-%2347a248.svg?style=for-the-badge&logo=mongodb&logoColor=white
[Mongo-url]: https://www.mongodb.com
[AmazonS3]: https://img.shields.io/badge/amazon%20s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white
[AmazonS3-url]: https://aws.amazon.com/s3/

# Strava Activity Recap

[Strava](https://www.strava.com/features) is a platform to store, analyze and share sports activities. This project aims to consume Strava's REST API to show the user a brief summary of their activities in a certain period of time.

## Technologies Used

### Server
- **Java**: Main programming language.
- **Spring Framework**:
  - **Spring Boot**: To handle server requests following an MVC architecture.
  - **FeignClient**: To interact with the Strava API.
  - **JUnit**: To perform unit tests and ensure code quality.

### Frontend
- **HTML, Javascript and CSS**

### Features

The user grants authorization for the application to read their stored activities on Strava.

Authorization is done through the client credentials flow of the OAuth2.0 standard.
![](/docs/PastedImage20240725200315.png)

Once authorization is granted, the user can select a date range to view a summary of their activities during that specific period. The summary displays the number of activities completed during that time, the total distance and duration, and a heatmap that highlights the most frequently traversed areas.
![](/docs/PastedImage20240725155020.png)



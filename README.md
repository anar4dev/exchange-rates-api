# exchange-rates-api
Exchange rates API from CBAR 
AZN to Foreigh currencies 

Exchange Rate API
The Exchange Rate API is a RESTful web service that provides real-time exchange rate for 1 AZN for different currencies. It is built using Java and Spring Boot.

Getting Started
To get started with the Exchange Rate API, follow these steps:

Clone the repository from GitHub:

API Endpoints
The following endpoints are available in the Exchange Rate API:

GET /rates/latest
Returns the latest exchange rates for a specific base currency.

Query Parameters:

base: The base currency to use for the exchange rates. Defaults to USD.
Example:


POST /exchange-rates/collect

Response:

json
Copy code
{
  "base": "EUR",
  "date": "2023-04-20",
  "rates": {
    "USD": 1.1228,
    "GBP": 0.8582,
    "AUD": 1.4815,
    "JPY": 123.9,
    "CAD": 1.4902
  }
}
GET /rates/{date}
Returns the exchange rates for a specific date.

Path Parameters:

date: The date to retrieve the exchange rates for in the format YYYY-MM-DD.
Query Parameters:

base: The base currency to use for the exchange rates. Defaults to USD.
Example:

bash
Copy code
GET /rates/2023-04-19?base=EUR
Response:

json
Copy code
{
  "base": "EUR",
  "date": "2023-04-19",
  "rates": {
    "USD": 1.1228,
    "GBP": 0.8582,
    "AUD": 1.4815,
    "JPY": 123.9,
    "CAD": 1.4902
  }
}
Security
The Exchange Rate API is secured using Spring Security. By default, only authenticated users are allowed to access the API. You can configure the security settings in the application.properties file.

Data Sources
The Exchange Rate API uses data from the Open Exchange Rates API. You will need to obtain an API key from their website to use the Exchange Rate API.

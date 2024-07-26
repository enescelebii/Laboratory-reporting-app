# Laboratory Reporting Application

Bu proje, Spring Boot kullanarak geliştirilmiş bir laboratuvar raporlama uygulamasıdır.

## Özellikler

- Laborant ekleme
- Laborant raporu ekleme
- Raporları listeleme
- Raporları arama
- Rapor dosyasını yükleme ve görüntüleme

## Gereksinimler

- Java 11 veya üstü
- Maven 3.6.3 veya üstü
- Spring Boot 2.5.4

## Kurulum

1. Bu projeyi yerel makinenize klonlayın:
    ```sh
    git clone https://github.com/kullanici_adi/laboratory-reporting-app.git
    ```

2. Proje dizinine gidin:
    ```sh
    cd laboratory-reporting-app
    ```

3. Gerekli bağımlılıkları yüklemek ve projeyi derlemek için Maven kullanın:
    ```sh
    mvn clean install
    ```

4. Uygulamayı çalıştırın:
    ```sh
    mvn spring-boot:run
    ```

## Nasıl çalışır
<p align = "center">
    <img src="https://github.com/enescelebii/laboratory-reporting-app/blob/main/uploaded-files/1.png">
</p>
<p align = "center">
    <img src="https://github.com/enescelebii/laboratory-reporting-app/blob/main/uploaded-files/2.png">
</p>
<p align = "left">
    <img src="https://github.com/enescelebii/laboratory-reporting-app/blob/main/uploaded-files/3.png">
</p>

## Kullanım
- **GET** request - localhost:8080/api/laborants/2
- Response HTTP with status 200 OK
```json
{
    "id": 2,
    "firstName": "Ahmet",
    "lastName": "Yılmaz",
    "hospitalIdentityNumber": "123456789",
    "reports": [
        {
            "id": 1,
            "fileNumber": "12345",
            "patientFirstName": "Enes",
            "patientLastName": "Celebi",
            "patientIdentityNumber": "98765432101",
            "diagnosisTitle": "Grip",
            "diagnosisDetails": "Hafif grip belirtileri.",
            "reportDate": "2024-07-22",
            "reportImagePath": a7535100-8e48-4b9c-9e54-e5253aff80b6_ExampleImage.png
        }
    ]
}
```
- **GET** request - localhost:8080/reports/9999
- Response with HTTP status 204 No content
- "Rapor bulunamadı"

- **GET** request - localhost:8080/api/reports/search?patientFirstName=Enes
- Response with HTTP status 200 OK
```json
[
    {
        "id": 1,
        "fileNumber": "12345",
        "patientFirstName": "Enes",
        "patientLastName": "Celebi",
        "patientIdentityNumber": "98765432101",
        "diagnosisTitle": "Grip",
        "diagnosisDetails": "Hafif grip belirtileri.",
        "reportDate": "2024-07-22",
        "reportImagePath": a7535100-8e48-4b9c-9e54-e5253aff80b6_ExampleImage.png
    }
]
```
#### Laborant Ekleme

- **URL:** `/api/laborants`
- **Method:** `POST`
- **Body:**
    ```json
    {
        "firstName": "Ahmet",
        "lastName": "Yılmaz",
        "hospitalIdentityNumber": "1234567890"
    }
    ```

#### Rapor Ekleme

- **URL:** `/api/reports`
- **Method:** `POST`
- **Body:**
    ```json
    {
        "fileNumber": "12345",
        "dia": "Enes",
        "patientLastName": "Celebi",
        "patientIdentityNumber": "98765432101",
        "diagnosisTitle": "Grip",
        "diagnosisDetails": "Hafif grip belirtileri.",
        "reportDate": "2024-07-22",
        "laborantId": 2
    }
    ```

#### Rapor Dosyası Yükleme
- Dosyayı yüklemek için POSTMAN da body->from-data->key kısmına file yazmanız gerekmekte
- **URL:** `/api/reports/{id}/upload`
- **Method:** `POST`
- **Form-Data:**
    - `file`: (rapor dosyası)

#### Raporları Listeleme

- **URL:** `/api/reports`
- **Method:** `GET`

#### Rapor Arama

- **URL:** `/api/reports/search`
- **Method:** `GET`
- **Query Params:**
    - `patientFirstName` (opsiyonel)
    - `patientLastName` (opsiyonel)
    - `patientIdentityNumber` (opsiyonel)
    - `laborantFirstName` (opsiyonel)
    - `laborantLastName` (opsiyonel)

## Endpoints

- [Report](#report)
- [ReportImage](#reportimage)
- [Laborants](#laborants)

### Report


| HTTP Method | Endpoint          | Description                                            |
| ----------- | ----------------- | ------------------------------------------------------ |
| GET         | /api/reports      | Retrieve all reports                                   |
| GET         | /api/reports/{id} | Retrieve a specific report by its ID                   |
| GET         | /api/reports/asc  | Retrieve all reports in ascending order by report date |
| POST        | /api/reports      | Create a new report                                    |
| PUT         | /api/reports/{id} | Update an existing report                              |
| DELETE      | /api/reports/{id} | Delete a specific report by its ID                     |


### ReportImage

| HTTP Method | Endpoint                   | Description                                 |
| ----------- | -------------------------- | ------------------------------------------- |
| GET         | /api/reports/{id}/image    | Retrieve a report image by report Id        |
| POST        | /api/reports/{id}/upload   | Upload a new report image                   |


### Laborants

| HTTP Method | Endpoint             | Description                                                |
| ----------- | -------------------- | -----------------------------------------------------------|
| GET         | /api/laborants       | Retrieve all lab Laborants                                 |
| GET         | /api/laborants/{id}  | Retrieve a specific lab Laborants by its ID                |
| POST        | /api/laborants       | Create a new lab Laborant                                  |
| PUT         | /api/laborants/{id}  | Update an existing lab Laborant                            |
| DELETE      | /api/laborants/{id}  | Delete a specific lab Laborant by its ID                   |

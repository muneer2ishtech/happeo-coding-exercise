# happeo-coding-exercise

## How to

1. Pull Docker image from Docker Hub
    - See [How to run Docker Image pulled from Docker Hub](./HOW-TO-TECHNICAL.md#run-docker-image-pulled-from-docker-hub)
    - Alternatively you can pull the code from Github and build and run. See [HOW-TO-TECHNICAL.md](./HOW-TO-TECHNICAL.md)
2. Run Following APIs from cURL, Postman (or any other API tool)
    1. Create Provisioner Secret for Organisation
    2. Provision New User
        - Need JWT token
        - See [HOW-TO-JWT.md](./HOW-TO-JWT.md)
    3. Find inactive Users
    4. Activate inactive Users


- See [APIs](./README.md#APIs) and [cURL Samples](./README.md#cURL-Samples) for details


## Assumptions
- No need of authentication for external admin
- One organization is using one provisioner only
    - If more than one is possible, then DB schema need to be updated by
       - Removing organisation_id, external_id from user table
       - And create mapping table with user_id, organisation_id, provisioner_id and external_id
- API to create organisation and provisioner mapping return Secret in response body
    - It is not safe to have secret in response body
- Encryting Secret before storing in DB is out of scope
- Sharing secrets in encrypted and secure way is out of scope
- Authentication / Authorization not implmented for any APIs except one in challenge (i.e. provisioning of users by external identity system)
- Did not code API to create Organisations
  - Inserted data of some sample organisations


## Improvements
- We can create is_org_admin in user table, as all users in an organisation can be admin, and whoever is admin will have this flag as true so they can only create secrets or activate / deactivate users in their organisation.
- Secret stored in DB without encryption, it is not safe (even though Base64 encoded), whoever has access to DB can access it. It need to be encrypted before storing and decrypted before comparing.
- Infact it is better to use public and private key combination.
  - Share public key with external identity system (provisioner)
  - Store private key of the organisation and use it code.
  - E.g. `JwtParserBuilder.verifyWith(SecretKey key)` we can use `JwtParserBuilder.decryptWith(PrivateKey key)`


## APIs

| Type                     | Auth | HTTP  | URL                                                                       | Description |
|--------------------------|------|-------|---------------------------------------------------------------------------|-------------|
| OpenAPI                  | None | GET   | /api-docs                                                                 | Spring generated API Documentation  |
| Swagger                  | None | GET   | /swagger-ui.html                                                          | Swagger generated API Documentation |
| Organisation Admin       | None | GET   | /api/provisioners                                                         | Get all existing Provisioners |
| Organisation Admin       | None | POST  | /api/organisations/{{organisationId}}/provisioners                        | Creates Organisation and Provisioner map and a secret for it. Creates Provisioner if not present. |
| Organisation Admin       | None | GET   | /api/organisations/{{organisationId}}/users                               | Finds Users matching the params, see [Pagination &amp; Sorting](./README.md#pagination--sorting) |
| Organisation Admin       | None | PATCH | /api/organisations/{{organisationId}}/activate-users                      | Activates inactive users belonging to organisation. If the user is already active, it does nothing, simply ignores those user IDs. If the user does not belong to organisation, it will not be activated, simply ignores those user IDs (No error thrown) |
| External Identity System | JWT  | POST  | /api/organisations/{{organisationId}}/provisioner/{{provisionerId}}/users | For provisioning new user by External identity system (Provisioner) |

## Pagination & Sorting
- Pagination
  - Page size param works only for 1 to Integer.MAX_VALUE
    - Add param `&size=n` for page size
  - If you want all results then setting size to zero does not work
  - Moving to different pages
    - param `&page=2` (for third page)
    - By default it fetches first page (i.e. `&page=0`)
- Sorting
  - Default sorting is by id in ascending order (in t_user)
  - Sorting by one attribute
    - `&sort=email,DESC`
  - Sorting by multiple attributes
    - `&sort=firstName,DESC&sort=lastName=ASC`
  - `ASC` is optional and implied

## cURL Samples
### Create Provisioner Secret for Organisation

```
curl --request POST --location 'http://localhost:8080/api/organisations/1/provisioners' \
--header 'Content-Type: application/json' \
--data '{
    "name": "pkta"
}'
```
- Success response
  - Response Code:  201 - Created

```
{
    "id": 2,
    "organisation": {
        "id": 2,
        "name": "Ishtech Oy"
    },
    "provisioner": {
        "id": 1,
        "name": "pkta"
    },
    "encodedSecret": "ZmY3Q2lTcE9iYUMxQUg4dUVhaUI5UGMzU2VHQjJuV3V0ak9PVGxXVDBHOXNXWmRFUXMxMUlyR1Z6eG5KeDltNw==",
    "decodedSecret": "ff7CiSpObaC1AH8uEaiB9Pc3SeGB2nWutjOOTlWT0G9sWZdEQs11IrGVzxnJx9m7"
}
```
- Error Responses
  - If `organisationId` is not valid or not present
    - Response Code: 400 - Bad Request

```
{
    "httpStatus": 400,
    "error": "Invalid organisationId"
}
```
  - If `organisationId` and `provisionerId` already exists (we have unique constraint for this combination)
      - Response Code: 400 - Bad Request

```
{
    "httpStatus": 400,
    "error": "Provisioner already exists for Organisation"
}
```

### Provision New User
```
curl --request --location 'http://localhost:8080/api/organisations/2/provisioner/1/users' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyIiwiaWF0IjoxNTE2MjM5MDIyfQ.dG_kxtNjn60nYi_f-jZBVU7POykUom6Y8UC7As-HejA' \
--data-raw '{
    "email": "muneer@ishtech.fi",
    "name": {
        "firstName": "Muneer",
        "lastName": "Syed"
    },
    "id": "1001"
}'
```
- Success Response
  - Response Code: 201 - Created

```
{
    "email": "muneer@ishtech.fi",
    "name": {
        "firstName": "Muneer",
        "lastName": "Syed"
    },
    "id": "1001",
    "applicationId": "1"
}
```
- Error Responses
  - If a request comes with `email` that is already existing
    - Response Code: 400 - Bad Request

```
{
    "httpStatus": 400,
    "error": "User with input email already exists"
}
```
  - If a request comes with already existing external `id` for `organisationId` (we have unique constraint for this combination)
      - Response Code: 400 - Bad Request

```
{
    "httpStatus": 400,
    "error": "User with input external Id already exists for organisationId"
}
```

### Activate inactive Users

```
curl --location --request PATCH 'http://localhost:8080/api/organisations/4/activate-users' \
--header 'Content-Type: application/json' \
--data '[
    1,
    2,
    3
]'
```
- Success Response
  - Response Code: 200 - Ok
  - No response boy

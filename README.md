# happeo-coding-exercise

## Assumptions
- No need of authentication for external admin
- One organization is using one provisioner only
    - If more than one is possible, then DB schema need to be updated by
       - Removing organisation_id, external_id from user table
       - And create mapping table with user_id, organisation_id, provisioner_id and external_id
- Secret generated is shared manually with the organisation and not in current scope retreive it by API
- Encryting Secret before storing in DB is out of scope

## Improvements
- We can create is_org_admin in user table, as all users in an organisation can be admin, and whoever is admin will have this flag as true so they can only create secrets or activate / deactivate users in their organisation.
- Secret stored in DB without encryption, it is not safe, whoever has access to DB can access it. It need to be encrypted before storing and decrypted before comparing.
- Infact it is better to use public and private key combination.
  - Share public key with external identity system (provisioner)
  - Store private key of the organisation and use it code.
  - E.g. `JwtParserBuilder.verifyWith(SecretKey key)` we can use `JwtParserBuilder.decryptWith(PrivateKey key)`


## APIs

| Type                     | HTTP  | URL                                                                       | Description |
|--------------------------|-------|---------------------------------------------------------------------------|-------------|
| OpenAPI                  | GET   | /api-docs                                                                 | Spring generated API Documentation  |
| Swagger                  | GET   | /swagger-ui.html                                                          | Swagger generated API Documentation |
| Orgnisation Admin        | GET   | /api/provisioners                                                         | Get all existing Provisioners |
| Orgnisation Admin        | GET   | /api/organisations/{orgnisationId}/provisioners                           | Creates Organisation and Provisioner map and a secret for it. Creates Provisioner if not present. |
| Orgnisation Admin        | POST  | /api/organisations/{orgnisationId}/users                                  | Finds Users matching the params, see [Pagination & Sorting](#Pagination & Sorting) |
| Orgnisation Admin        | PATCH | /api/organisations/{orgnisationId}/activate-users                         | Activates inactive users belonging to organisation. If the user is already active, it does nothing, simply ignores those user IDs. If the user does not belong to organisation, it will not be activated, simply ignores those user IDs (No error thrown) |
| External Identity System | POST  | /api/organisations/{{organisationId}}/provisioner/{{provisionerId}}/users | For provisioning new user by External identity system (Provisioner) |

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

## How to

### Docker Image pulled from Docker Hub
- [How to run Docker Image pulled from Docker Hub](./HOW-TO-TECHNICAL.md#run-docker-image-pulled-from-docker-hub) without making any builds locally

#### For builds / local run etc
See [How-to-technical](./HOW-TO-TECHNICAL.md) on how to make maven, docker builds and push & pull images to Docker Hub

### How to use system functionally (Business Operations)
  - See [How-to-functional](./HOW-TO-FUNCTIONAL.md)

## To find users to activate
- `http://localhost:8080/api/organisations/{{organisationId}}/users?isActive=false&unpaged=true`

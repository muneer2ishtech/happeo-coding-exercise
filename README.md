# happeo-coding-exercise

## Assumptions
- No need of authentication for external admin
- One organization is using one provisioner only
    - If more than one is possible, then DB schema need to be updated by
       - Removing organisation_id, external_id from user table
       - And create mapping table with user_id, organisation_id, provisioner_id and external_id
- Secret generated is shared manually with the organisation and not in current scope retreive it by API

## Improvements
- We can create is_org_admin in user table, as all users in an organisation can be admin, and whoever is admin will have this flag as true so they can only create secrets or activate / deactivate users in their organisation.

## API Docs

| Type               | HTTP  | URL                                     |
|--------------------|-------|-----------------------------------------|
| OpenAPI            | GET   | localhost:8080/api-docs                 |
| Swagger            | GET   | localhost:8080/swagger-ui.html          |


## Pagination & Sorting
- Add param `&size=n` for page size
- Page size param works only for 1 to Integer.MAX_VALUE
- If you want all results then setting size to zero does not work
- You need send custom param `unpaged=true`
- e.g. `http://localhost:8080/api/organisations/{{organisationId}}/users?isActive=true&page=3&size=5`
- Param for sorting `&sort=email,DESC`

## To find users to activate
- `http://localhost:8080/api/organisations/{{organisationId}}/users?isActive=false&unpaged=true`

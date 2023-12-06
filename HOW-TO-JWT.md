# How to get JWT to use for authentication in the application
- Use https://jwt.io

## How to get JWt using simple secret (not Base64 encoded)
[jwt.io screenshot for simple JWT](src/main/resources/static/images/jwt_decoded_instructions.png)
1. Ensure HS256 is selected for Algorithm
2. In PAYLOAD:DATA, put `organisationId` for `sub`
3. In VERIFY SIGNATURE, Copy and paste `decodedSecret` from API response of `POST` - `/api/organisations/{{organisationId}}/users`
4. Ensure "secret base64 encoded" as unchecked
5. Copy Generated token and use

## How to get JWt using Base64 encoded secret
[jwt.io screenshot for simple JWT](src/main/resources/static/images/jwt_encoded_instructions.png)
1. Ensure HS256 is selected for Algorithm
2. In PAYLOAD:DATA, put `organisationId` for `sub`
3. In VERIFY SIGNATURE, Copy and paste `decodedSecret` from API response of `POST` - `/api/organisations/{{organisationId}}/users`
4. Ensure "secret base64 encoded" as checked
5. Copy Generated token and use

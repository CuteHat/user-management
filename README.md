# User management

## start the app

at the root of the project run ./start.sh or ./start.ps1 depending on your OS,
internally this will run docker-compose up -d, so make sure you have docker installed and running.

## Database

The database is a postgres database, the schema is created automatically when the app starts.
flyway is used to manage the database migrations, the migrations are located in the resources/db/migration
and resources/db/migration_dev folders, the latter is only run when the app is started with dev profile,
currently the only difference is that the dev profile will create a test users.

## generated users

as mentioned above, the dev profile will create a test users, the users are:

```
ericcartman@gmail.com / 12345678
administrator@gmail.com / 12345678
```

Administrator has the role ADMINISTRATOR, you can use this to invoke the admin endpoints.

## Security

admin endpoint(s) are prefixed with /admin, and require the ADMINISTRATOR role to be invoked.
User endpoint(s) are prefixed with /user, and require authentication to be invoked.
Auth endpoint(s) are prefixed with /auth, and do not require authentication to be invoked.

login endpoint requires JWT token, the token contains default jwt claims and additionally the user id and role.
the token is signed with a secret key, the secret key is stored in resources/certs/private.pem, the public key is stored
in resources/certs/public.pem (that is the key that is used to verify the token).

## Validation

for validation, spring boot starter validation is used, the validation is done on the DTOs. validation errors are
returned as a list of errors, each error contains the field name and the error message.

## Caching

for caching, spring boot starter cache is used, the cache is configured to use hazelcast. configuration file is
located in resources/hazelcast.yaml.

## Coverage

Jacoco is used for coverage, the coverage report is located in build/jacocoHtml/index.html, the report is generated
when running the tests.

## API

swagger: http://localhost:8080/admin/swagger-ui.html
openapi: http://localhost:8080/admin/v3/api-docs

## User Controller

### Get Authenticated User

- **Method:** `GET`
- **Endpoint:** `/api/v1/user`
- **Description:** Get authenticated user using bearer token.

### Update Authenticated User

- **Method:** `PUT`
- **Endpoint:** `/api/v1/user`
- **Description:** Update authenticated user, request fields are validated.

## Admin User Controller

### Get User by ID

- **Method:** `GET`
- **Endpoint:** `/api/v1/admin/users/{id}`
- **Description:** Get user information by ID.

### Update User

- **Method:** `PUT`
- **Endpoint:** `/api/v1/admin/users/{id}`
- **Description:** Update user by ID, admin can update role too.

### Delete User

- **Method:** `DELETE`
- **Endpoint:** `/api/v1/admin/users/{id}`
- **Description:** Delete user by ID.

### Create User

- **Method:** `POST`
- **Endpoint:** `/api/v1/admin/users`
- **Description:** Create a new user.

### Deactivate User

- **Method:** `PATCH`
- **Endpoint:** `/api/v1/admin/users/{id}/deactivate`
- **Description:** Deactivate user by ID. Deactivated users cannot login.

### Activate User

- **Method:** `PATCH`
- **Endpoint:** `/api/v1/admin/users/{id}/activate`
- **Description:** Activate user by ID. Activated users can login.

### Filter Users

- **Method:** `GET`
- **Endpoint:** `/api/v1/admin/users/filter`
- **Description:** Filter users. Can be called without filters to return all users.

## Auth Controller

### User Login

- **Method:** `POST`
- **Endpoint:** `/api/v1/auth/login`
- **Description:** Authenticate user and return access token. Deactivated users cannot login.
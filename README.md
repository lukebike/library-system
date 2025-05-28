## Books
### GET routes
- **/books** sample data - none
- **/books/search** -- sample data (http://localhost:8080/books/search?title=pippi longstocking)

### POST routes
- **/books** sample data -

```json
{
"title": "Sample Book",
"publicationYear": 2025,
"availableCopies": 3,
"totalCopies": 3
}
```

## Authors
### GET routes
- **/authors** - sample data - none
- **/authors/name/{lastName}** - sample data - (http://localhost:8080/authors/name/kafka)

### POST routes
- **/authors** sample data -

```json
{
  "firstName": "New",
  "lastName": "Author",
  "nationality": "Swedish"
}
```

## Users
### GET routes
- **/users/email/{email}** - sample data - (http://localhost:8080/users/email/anna.andersson@email.com)

### POST routes
- **/users** - sample data -

```json
{
    "firstName": "New",
    "lastName": "User",
    "password": "newPassword",
    "email": "newuser@gmail.com"
}
```

## Loans
### GET routes
- **/loans/{userId}** - sample data - (http://localhost:8080/loans/1)

### POST routes
- **/loans** - sample data -

```json
{
  "bookId": 50,
  "userId": 3
}
```

### PUT routes
- **PUT /loans/{id}/return** - sample data - (http://localhost:8080/loans/31/return)
- **PUT /loans/{id}/extend** - sample data - (http://localhost:8080/loans/57/extend)
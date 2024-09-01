Feature: API REST Usuario File

Background:
    * def user =
    """
        {
            "id": '#number',
            "name": '#string',
            "email": '#string',
            "password": '#string',
            "phone": '#string'
        }
    """

Scenario: Consultar todos los usuarios
    Given url 'http://localhost:8080'
    When path '/user'
    When method GET
    Then status 200
    And match each response == user

Scenario: Consultar un usuarios por el mail
    Given url 'http://localhost:8080'
    When path '/user/admin@mail.com'
    When method GET
    Then status 200

Scenario: Consultar un usuaio enpoint JSON from file
    Given url 'http://localhost:8080'
    When path '/user'
    When method GET
    Then status 200
    And match each response == user
    And match response == read('../responses/Usuario.json')

Scenario: Añadir Usuario enpoint JSON from file
    Given url 'http://localhost:8080'
    When path '/user/register'
    And request read('../responses/UsuarioRequest.json')
    When method POST
    Then status 200
    And match response ==  { "response": "Usuario registrado correctamente" }

Scenario: Actualizar un usuario
    Given url 'http://localhost:8080'
    When path '/user/testKarate@mail.com'
    And request read('../responses/UsuarioUpdate.json')
    When method PUT
    Then status 200


Scenario: Eliminar usuario
    Given url 'http://localhost:8080'
    When path '/user/testKarate@mail.com'
    And request read('../responses/UsuarioRequest.json')
    When method DELETE
    Then status 200
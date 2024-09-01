Feature: API REST favorito File

Background:
  * def favorito =
  """
    {
      iduser: '#number',
      idanime: '#number'
    }
  """

Scenario: Consulta de los favoritos
  Given url 'http://localhost:8080'
  And request read('../responses/Favorito.json')
  When path '/favorito'
  And method GET
  Then status 200
  And match each response == favorito

Scenario: Consulta de favorito mediante id
  Given url 'http://localhost:8080'
  And path '/favorito/1'
  And method GET
  Then status 200
  And match each response == favorito

Scenario: Añadir anime by JSON file
  Given url 'http://localhost:8080'
  And path '/favorito/add'
  And request read('../responses/Favorito.json')
  And method POST
  Then status 200
  And match response == { "response" : "Favorito" }

#  Scenario:
#    Given url 'http://localhost:8080'
#    And path '/favorito/18'
#    And request read('../responses/Favorito.json')
#    And method PUT
#    Then status 200
#    And match response == favorito

Scenario: Eliminación de favorito
  Given url 'http://localhost:8080'
  And path '/favorito/2/5'
  And method DELETE
  Then status 200


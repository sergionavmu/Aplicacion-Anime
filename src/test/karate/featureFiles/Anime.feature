Feature: API REST Anime File
Background:
  * def anime =
  """
    {
      id: '#number',
      name: '#string',
      description: '#string',
      type: '#string',
      year: '#number',
      image: '#string',
      originalName: '#string',
      rating: '#string',
      demography: '#string',
      genre: '#string',
      image2: '#string',
      active: '#boolean'
    }
  """

Scenario: Consultar usuarios formato JSON
  Given url 'http://localhost:8080'
  And request read('../responses/Anime.json')
  When path '/anime'
  And method GET
  Then status 200
  And match each response == anime

Scenario: Consultar un usuario por id
  Given url 'http://localhost:8080'
  And path '/anime/1'
  And method GET
  Then status 200
  And match response == anime

Scenario: Consultar un anime por nombre
  Given url 'http://localhost:8080'
  And path '/anime/name/Sonny Boy'
  And method GET
  Then status 200
  And match response == anime

Scenario: Añadir un anime
  Given url 'http://localhost:8080'
  And path '/anime/add'
  And request read('../responses/AnimeRequest.json')
  And method POST
  Then status 201
# And match response == {"reponse": "Anime agregado correctamente"}

Scenario: Actualizar un anime por id
  Given url 'http://localhost:8080'
  And path '/anime/28'
  And request read('../responses/AnimeUpdate.json')
  And method PUT
  Then status 200
# And match response == {"reponse": "Anime actualizado correctamente"}

Scenario: Eliminar un anime por id
  Given url 'http://localhost:8080'
  And path '/anime/28'
  And method DELETE
  Then status 200


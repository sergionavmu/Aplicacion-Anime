Feature: API REST Video File

Background:
  * def video =
  """
    {
      "idanime": '#number',
      "episode": '#number',
      "url": '#string',
      "image": '#string'
    }
  """

Scenario: Consultar todos los videos
  Given url 'http://localhost:8080'
  When path '/videos'
  When method GET
  Then status 200
  And match each response == video

Scenario: Consultar un video por el id del anime
  Given url 'http://localhost:8080'
  When path '/videos/1'
  When method GET
  Then status 200
  And match each response == video

Scenario: Consultar un video enpoint JSON from file
  Given url 'http://localhost:8080'
  When path '/videos'
  When method GET
  Then status 200
  And match each response == video
  And match response == read('../responses/Video.json')

Scenario: Añadir un video con enpoint JSON from file
  Given url 'http://localhost:8080'
  When path '/videos/add'
  And request read('../responses/VideoRequest.json')
  When method POST
  Then status 201


Scenario: Actualizar un video
  Given url 'http://localhost:8080'
  When path '/videos/anime/27/episode/26'
  And request read('../responses/VideoUpdate.json')
  When method PUT
  Then status 200


Scenario: Eliminar video
  Given url 'http://localhost:8080'
  When path '/videos/anime/27/episodio/26'
  When method DELETE
  Then status 200

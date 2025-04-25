package com.apilivros.Services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apilivros.Domain.Books;
import com.apilivros.Domain.enums.GenreMapper;
import com.apilivros.Repository.BooksRepository;
import com.apilivros.Services.Exceptions.ResourceNotFoundException;

@Service
public class BooksImportService {

    @Autowired
    private BooksRepository booksRepository;

    public void importarLivros(String busca) {
        try {
            String url = "https://openlibrary.org/search.json?q=" + busca.replace(" ", "+");
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
    
            if (connection.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder responseContent = new StringBuilder();
    
                while ((inputLine = in.readLine()) != null) {
                    responseContent.append(inputLine);
                }
                in.close();
    
                JSONObject jsonResponse = new JSONObject(responseContent.toString());
                JSONArray docs = jsonResponse.getJSONArray("docs");
    
                List<String> subjectList = new ArrayList<>();
                subjectList.add(busca.toLowerCase());
    
                int limit = Math.min(docs.length(), 50); 
                for (int i = 0; i < limit; i++) {
                    JSONObject bookJson = docs.getJSONObject(i);
    
                    Books book = new Books();
                    book.setTitle(bookJson.optString("title"));
    
                    if (booksRepository.existsByTitle(book.getTitle())) {
                        continue; 
                    }
    
                    JSONArray authorsArray = bookJson.optJSONArray("author_name");
                    if (authorsArray != null && authorsArray.length() > 0) {
                        book.setAuthor(authorsArray.getString(0));
                    } else {
                        book.setAuthor("Unknown Author");
                    }
    
                    book.setRating(null);
    
                    if (bookJson.has("cover_i")) {
                        Integer coverId = bookJson.getInt("cover_i");
                        String coverUrl = "https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg";
                        book.setImageUrl(coverUrl);
                    } else {
                        book.setImageUrl(null);
                    }

                    String publishYear = bookJson.optString("first_publish_year", "Data de publicação não disponível");
                   
                    book.setDescription(String.format("Livro escrito por %s do gênero %s, lançado em %s", 
                    book.getAuthor(), 
                    String.join(", ", subjectList), 
                    publishYear));
    
                    List<Integer> genreCodes = GenreMapper.mapSubjectsToGenreCodes(subjectList);
                    book.setGenresCode(genreCodes);
    
                    booksRepository.save(book);
                }
            } else {
                throw new ResourceNotFoundException("Erro ao buscar livros: " + connection.getResponseCode());
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erro ao importar livros. Detalhes: " + e.getMessage());
        }
    }
}

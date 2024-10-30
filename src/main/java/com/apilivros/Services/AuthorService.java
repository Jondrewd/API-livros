package com.apilivros.Services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.apilivros.Domain.Author;
import com.apilivros.Dto.AuthorDTO;
import com.apilivros.Repository.AuthorRepository;
import com.apilivros.Services.Exceptions.ResourceNotFoundException;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Page<AuthorDTO> findAll(Pageable pageable){
        Page<Author> authors = authorRepository.findAll(pageable);
        return authors.map(this::convertToDTO);
    }

    public AuthorDTO findById(Integer id){
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado."));
        return convertToDTO(author);
    }

    public Author insert(Author obj) {
        return authorRepository.save(obj);
    }

    public void delete(Integer id) {
        findById(id);
        authorRepository.deleteById(id);
    }

    public Author update(Integer id, Author obj) {
        Optional<Author> newObj = authorRepository.findById(id);
        Author author = newObj.get();
        updateAuthor(author, obj);
        return authorRepository.save(author);
    }
    
    public Author findByAuthorname(String authorname) {
        return authorRepository.findByAuthorname(authorname);
    }

    public void updateAuthor(Author author, Author obj) {
        author.setName(obj.getName());
        author.setDescription(obj.getDescription());
        author.setNationality(obj.getNationality());
    }

    private AuthorDTO convertToDTO(Author author) {
        return new AuthorDTO(author);
    }

}
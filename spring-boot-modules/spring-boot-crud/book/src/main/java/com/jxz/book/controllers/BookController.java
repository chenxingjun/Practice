package com.jxz.book.controllers;

import com.jxz.book.entities.Book;
import com.jxz.book.repositories.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("index")
    public String bookList(Model model){
        model.addAttribute("books", bookRepository.findAll());
        return "index";
    }

    @GetMapping("/addBook")
    public String showAddForm(Book book) {
        return "add-book";
    }

    @PostMapping("/saveBook")
    public String saveBook(@Valid Book book, BindingResult result, Model model){
        if(result.hasErrors()){
            return "add-book";
        }
        bookRepository.save(book);
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);

        return "update-book";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") long id, @Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            return "update-book";
        }

        bookRepository.save(book);

        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Book Id:" + id));
        bookRepository.delete(book);

        return "redirect:/index";
    }

}

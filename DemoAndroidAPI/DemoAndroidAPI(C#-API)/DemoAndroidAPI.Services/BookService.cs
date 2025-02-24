using DemoAndroidAPI.Repositories;
using DemoAndroidAPI.Repositories.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static DemoAndroidAPI.Repositories.DTOs.CreateBookDTO;
using static DemoAndroidAPI.Repositories.DTOs.UpdateBookDTO;

namespace DemoAndroidAPI.Services
{
    public interface IBookService
    {
        Task<List<Book>> GetAll();
        Task<Book> GetById(int id);
        Task<int> Create(CreateBookRequestDTO book);
        Task<int> Update(int id, UpdateBookRequestDTO book);   
        Task<bool> Delete(int id);
    }
    public class BookService : IBookService
    {
        private readonly BookRepository _repository;

        public BookService()
        {
            _repository = new BookRepository();
        }

        public async Task<int> Create(CreateBookRequestDTO bookCreated)
        {
            Book book = new Book(bookCreated.Name, bookCreated.Description, bookCreated.Price, bookCreated.IsSold);
            await _repository.CreateAsync(book);
            return book.Id;
        }

        public async Task<bool> Delete(int id)
        {
            var book = _repository.GetById(id);
            if(book != null)
            {
                return await _repository.RemoveAsync(book);
            }
            return false;
        }

        public async Task<List<Book>> GetAll()
        {
            return await _repository.GetAllAsync();
        }

        public async Task<Book> GetById(int id)
        {
            return await _repository.GetByIdAsync(id);
        }

        public async Task<int> Update(int id, UpdateBookRequestDTO bookUpdated)
        {
            var book = _repository.GetById(id);
            if (book != null)
            {
                book.Name = bookUpdated.Name;  
                book.Description = bookUpdated.Description;
                book.Price = bookUpdated.Price;
                book.IsSold = bookUpdated.IsSold;
                return await _repository.UpdateAsync(book);
            }
            return 0;
        }
    }
}

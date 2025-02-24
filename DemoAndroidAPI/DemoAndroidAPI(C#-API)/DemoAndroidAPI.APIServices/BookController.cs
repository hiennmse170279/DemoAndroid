using DemoAndroidAPI.Repositories.Models;
using DemoAndroidAPI.Services;
using Microsoft.AspNetCore.Mvc;
using static DemoAndroidAPI.Repositories.DTOs.CreateBookDTO;
using static DemoAndroidAPI.Repositories.DTOs.UpdateBookDTO;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace DemoAndroidAPI.APIServices
{
    [Route("api/[controller]")]
    [ApiController]
    public class BookController : ControllerBase
    {
        private readonly IBookService _bookService;

        public BookController(IBookService bookService)
        {
            _bookService = bookService;
        }

        // GET: api/<BookController>
        [HttpGet]
        public async Task<IEnumerable<Book>> Get()
        {
            return await _bookService.GetAll();
        }

        // GET api/<BookController>/5
        [HttpGet("{id}")]
        public async Task<Book> Get(int id)
        {
            return await _bookService.GetById(id);
        }

        // POST api/<BookController>
        [HttpPost]
        public async Task<int> Post(CreateBookRequestDTO book)
        {
            return await _bookService.Create(book);
        }

        // PUT api/<BookController>/5
        [HttpPut]
        public async Task<int> Put([FromQuery] int id, [FromBody] UpdateBookRequestDTO book)
        {
            return await _bookService.Update(id, book);
        }

        // DELETE api/<BookController>/5
        [HttpDelete("{id}")]
        public async Task<bool> Delete(int id)
        {
            return await _bookService.Delete(id);
        }
    }
}

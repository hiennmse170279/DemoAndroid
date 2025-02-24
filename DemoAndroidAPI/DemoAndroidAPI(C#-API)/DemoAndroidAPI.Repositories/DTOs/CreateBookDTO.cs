namespace DemoAndroidAPI.Repositories.DTOs;

public static class CreateBookDTO
{
    public class CreateBookRequestDTO
    {
        public CreateBookRequestDTO(string name, string description, int price, bool isSold)
        {
            Name = name;
            Description = description;
            Price = price;
            IsSold = isSold;
        }

        public string Name { get; set; }
        public string Description { get; set; }
        public int Price { get; set; }
        public bool IsSold { get; set; }
    }
}

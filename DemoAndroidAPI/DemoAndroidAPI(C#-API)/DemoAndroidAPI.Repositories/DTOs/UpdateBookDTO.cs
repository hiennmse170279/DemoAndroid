namespace DemoAndroidAPI.Repositories.DTOs;

public static class UpdateBookDTO
{
    public class UpdateBookRequestDTO
    {
        public UpdateBookRequestDTO(string name, string description, int price, bool isSold)
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

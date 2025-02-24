CREATE DATABASE DemoAndroid;
GO
Use DemoAndroid;
GO
CREATE TABLE Books (
    Id INT IDENTITY(1,1) PRIMARY KEY, -- Auto-incrementing primary key
    Name NVARCHAR(255),
    Description NVARCHAR(255),
    Price INT,
    IsSold BIT
);

GO
INSERT INTO Books (Name, Description, Price, IsSold)
VALUES 
(N'Tôi Nói Gì Khi Nói Về Chạy Bộ', N'Cuốn sách nói về chạy bộ', 60000, 0),
(N'Lắng Nghe Gió Hát', N'Cuốn sách nói về cuộc sống tuổi trẻ', 65000, 1) 
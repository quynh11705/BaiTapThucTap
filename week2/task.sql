CREATE DATABASE task_management;
GO

USE task_management;
GO

CREATE TABLE Users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL
);
GO

CREATE TABLE Tasks (
    id INT IDENTITY(1,1) PRIMARY KEY,
    title NVARCHAR(255) NOT NULL,
    description NVARCHAR(500),
    status NVARCHAR(50) DEFAULT 'Pending',
    user_id INT NOT NULL,
    CONSTRAINT FK_Tasks_Users FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);
GO

INSERT INTO Users (name, email, password) VALUES
(N'Nguyễn Văn A', 'a@gmail.com', 'kDX7uFf1e1'), 
(N'Trần Thị B', 'b@gmail.com', 'kDX7uFf1e2');
GO

INSERT INTO Tasks (title, description, status, user_id) VALUES
(N'Làm báo cáo', N'Hoàn thành báo cáo tuần', 'Pending', 1),
(N'Họp nhóm', N'Chuẩn bị họp dự án', 'Done', 2);
GO
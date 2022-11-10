<?php
require '../vendor/autoload.php';
use Dotenv\Dotenv;

$dotenv = new DotEnv("..");
$dotenv->load();

$host = getenv('DB_HOST');
$port = getenv('DB_PORT');
$db   = getenv('DB_NAME');
$user = getenv('DB_USERNAME');
$pass = getenv('DB_PASSWORD');

try {
    $dbConnection = new \PDO(
        "mysql:host=$host;port=$port;charset=utf8mb4;dbname=$db",
        $user,
        $pass
    );

    $statement = $dbConnection->query("SELECT * FROM `result`");
    $result = $statement->fetch();
    
    header('Content-Type: application/json; charset=utf-8');
    echo json_encode($result);
} catch (\PDOException $e) {
    exit($e->getMessage());
}
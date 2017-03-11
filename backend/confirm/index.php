<?
include("../api/config.php");

if(!isset($_GET['code']))
	exit("No confirmation code was present! Please try clicking on the link provided in the email confirmation again!");

if(!validCode($_GET['code']))
	exit("The provided confirmation code was not valid! Please try clicking on the link provided in the email confirmation again!");

updateVerified($_GET['code']);
$email = getEmail($_GET['code']);
$school = parseSchool($email);
if(!schoolExists($school))
	insertSchool($school);
exit("You have verified yourself as a university student! In a few seconds AnonU will show you all the posts in your area!");

function insertSchool($school) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);

	$result = mysql_query("INSERT INTO `anonkhdi_core`.`schools` (`id`, `name`, `logo_link`) VALUES (NULL, '".$school."', '');") or trigger_error(mysql_error());
}

function schoolExists($school) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);

	$result = mysql_query("SELECT * FROM `schools`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if(strtolower($school) == $row['name'])
			return true;
	}
	return false;
}

function parseSchool($email) {
	//colejelinek@u.boisestate.edu
	//postdoc@grad.ucla.edu
	//student@school.edu
	$parts = explode(".", $email);
	$school = $parts[count($parts) - 2];
	if (strpos($school, '@') !== false) {
		$parts = explode("@", $school);
		$school = $parts[1];
	}
	return strtolower($school);
}

function getEmail($code) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);

	$result = mysql_query("SELECT * FROM `users`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if($row['email_token'] == $code)
			return $row['email'];
	}
}

function updateVerified($code) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);

	$result = mysql_query("UPDATE  `anonkhdi_core`.`users` SET  `email_verified` =  '1' WHERE  `users`.`email_token` = '".$code."';") or trigger_error(mysql_error());
}

function validCode($code) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);

	$result = mysql_query("SELECT * FROM `users`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if(strtolower($code) == strtolower($row['email_token']))
			return true;
	}
	return false;
}
?>
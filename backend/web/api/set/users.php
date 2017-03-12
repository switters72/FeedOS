<?
ini_set('display_startup_errors',1);
ini_set('display_errors',1);
error_reporting(-1);

include("../config.php");

if(!isset($_GET['email']))
	exit(json_encode(array('successful' => false, 'error' => "no email provided")));

if(!validEmail($_GET['email']))
	exit(json_encode(array('successful' => false, 'error' => "email is not a university email(".$_GET['email'].")")));

if(userExists($_GET['email']))
	exit(json_encode(array('successful' => false, 'error' => "email already exists")));

$email = $_GET['email'];
$school = parseSchool($_GET['email']);

$tokens = insertUser($email);
$token = $tokens["token"];
$emailToken = $tokens['email_token'];
sendConfirmationEmail($email, $emailToken);
$user = getUser($token);
if(!schoolExists($school)) {
	insertSchool($school);
	$schoolId = getSchoolId($school);
	insertPost(0, $schoolId, "AnonU has landed at ".$school."!");
}
$schoolId = getSchoolId($school); 
exit(json_encode(array('successful' => true, "id" => $user["id"], 'token' => $token, "score" => $user["score"], "school" => $school, "home_school_id" => $schoolId)));

function insertPost($userid, $schoolId, $contents) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("INSERT INTO `anonkhdi_core`.`posts` (`id`, `owner_id`, `school_id`, `date_utc`, `contents`) VALUES (NULL, '".$userid."', '".$schoolId."', '".time()."', '".mysql_real_escape_string($contents)."');") or trigger_error(mysql_error());
}

function getSchoolId($school) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);

	$result = mysql_query("SELECT * FROM `schools`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if($school == $row['name'])
			return $row['id'];
	}
}

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
		if(strtolower($school) == strtolower($row['name']))
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

function validEmail($email) {
	if (strpos($email, '.') !== false) {
		$parts = explode(".", $email);
		if(strtolower($parts[count($parts)-1]) == "edu")
			return true;
	}
	return false;
}

function getUser($token) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);

	$result = mysql_query("SELECT * FROM `users`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if(strtolower($token) == strtolower($row['token']))
			return array("id" => (int)$row['id'], "email" => $row['email'], "score" => (int)$row['score'], "token" => $row['token']);
	}
	return null;
}

function insertUser($email) {
	$token = ranString();
	$emailToken = ranString();
	
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("INSERT INTO `anonkhdi_core`.`users` (`id`, `email`, `score`, `token`, `email_token`, `email_verified`) VALUES (NULL, '".$email."', '0', '".$token."', '".$emailToken."', '0');") or trigger_error(mysql_error());
	
	return array("token" => $token, "email_token" => $emailToken);
}

function userExists($email) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);

	$result = mysql_query("SELECT * FROM `users`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if(strtolower($email) == strtolower($row['email']))
			return true;
	}
	return false;
}

function ranString($length = 10) {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyz';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}

function sendConfirmationEmail($email, $email_token) {
	$headers = 'From: noreply@anonymousuniversity.com' . "\r\n" .
    'Reply-To: noreply@anonymousuniversity.com';
	$message = "Hey there!\r\n\r\nThanks for showing interest in AnonU! Please click the confirmation link gain access to the AnonU service!\r\n\r\nConfirmation link: https://anonymousuniversity.com/confirm/".$email_token."\r\n\r\nWelcome to the community!\r\nAonU Team";
	mail($email, "AnonU Confirmation", $message, $headers);
}
?>
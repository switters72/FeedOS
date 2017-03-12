<?

include("../session.php");

$schools = getSchools();
exit(json_encode(array("schools" => $schools)));


function getSchools() {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);

	$schools = array();
	$result = mysql_query("SELECT * FROM `schools`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		array_push($schools, array("id" => $row['id'], "name" => $row['name'], "logo_link" => $row['logo_link']));
	}
	return $schools;
}

?>
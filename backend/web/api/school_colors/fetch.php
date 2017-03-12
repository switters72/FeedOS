<?

if(!isset($_GET['school']))
	exit(json_encode(array('successful' => false, "error" => "no school provided")));

$title = getTitle($_GET['school']);
if($title == -1)
	exit(json_encode(array("successful" => false, "error" => "query failed")));

$pageid = getPageId($title);
if($pageid == -1)
	exit(json_encode(array("successful" => false, "error" => "page id failed")));

//$colors = getColors($pageid);
$colors = getColorsFromTitle($title);
var_dump($colors);

function getColorsFromTitle($title) {
	$cnts = file_get_contents("https://en.wikipedia.org/wiki/".$title);
	exit($cnts);
	$c=preg_match_all ("/(#{1}(?:[A-F0-9]){6})(?![0-9A-F])", $cnts, $matches);
	exit($c."|".var_dump($matches));
	
	/*if ()
	{
	  $htmlcol1=$matches[1][0];
	  print "($htmlcol1) \n";
	  exit("INSIDE");
	}*/
	exit("");
}

function getColors($pageid) {
	$cnts = file_get_contents("https://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&format=json&pageids=".$pageid."&rvsection=0");
	if (strpos($cnts, '#REDIRECT') !== false) {
		$p1 = explode("#REDIRECT [[", $cnts);
		$p2 = explode("]]", $p1[1]);
		$title = $p2[0];
		$pageid = getPageId($title);
		if($pageid == -1)
			exit(json_encode(array("successful" => false, "error" => "page id failed")));
		$cnts =  file_get_contents("https://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&format=json&pageids=".$pageid."&rvsection=0");
	}
	/*$parts = explode("|#", $cnts);
	$part1 = $parts[1];
	$part2 = $parts[2];
	$p1 = explode("{", $part1);
	$p2 = explode("}", $part2);
	$color1 = "#".$p1[0];
	$color2 = "#".$p2[0];
	return array($color1, $color2);*/
	exit($cnts);
	$html = preg_replace("/^#[0-9a-f]{3,6}$/i", $cnts, $html);
	exit($html."");
}

function getPageId($schoolName) {
	$cnts = file_get_contents("https://en.wikipedia.org/w/api.php?action=query&titles=".urlencode($schoolName)."&format=json");
	$json = json_decode($cnts, true);
	if(count($json['query']['pages']) > 0) {
		$page = $json['query']['pages'];
		foreach($page as $element)
			return $element['pageid'];
	}
	else {
		return -1;
	}
		
}

function getTitle($query) {
	$cnts = file_get_contents("https://en.wikipedia.org/w/api.php?action=opensearch&search=".$query."%20university&limit=1&namespace=0&format=json");
	$parts = explode("\"", $cnts);
	return $parts[3];
}

?>
import ReactWeather, { useOpenWeather } from "react-open-weather";
​
function Weather(props) {
  const { data, isLoading, errorMessage } = useOpenWeather({
    key: "014c50ec2883f806548394690f9af61c",
    lat: "48.137154",
    lon: "11.576124",
    lang: "en",
    unit: "metric", // values are (metric, standard, imperial)
  });
  return (
    <div
      style={{
        width: "600px",
        height: "400px",
        margin: "auto",
        position: "absolute",
        top: "50px",
        left: "30px",
      }}
    >
      <ReactWeather
        isLoading={isLoading}
        errorMessage={errorMessage}
        data={data}
        lang="en"
        locationLabel="bethlehem"
        unitsLabels={{ temperature: "C", windSpeed: "Km/h" }}
        showForecast
      />
    </div>
  );
}
​
export default Weather;
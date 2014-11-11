'use strict';
var rcMod = angular.module('rcApp');

rcMod.controller('DashboardCtrl', function ($scope, $resource, $filter, $http, SessionService) {
  $scope.sid = SessionService.get("sid");

  // TEMPORARY... FIXME!
  var Account = $resource('/restcomm/2012-04-24/Accounts.:format/:accountSid',
    { accountSid:$scope.sid, format:'json' },
    {
      // charge: {method:'POST', params:{charge:true}}
    });

  $scope.accountData = Account.get();



}); //end controller DashboardCtrl



//starting chart
rcMod.controller('Totalcallslast24hoursCtrl', function($scope, $http){

$scope.returnValue = $http.get("/restcomm/2012-04-24/Accounts/ACae6e420f425248d6a26948c17a9e2acf/Statistics/Totalcallslast24hours.json");  
		$scope.returnValue.success(function(data) {

	  		$scope.Totalcallslast24hours = data;
			//alert(data);	

		});
		$scope.returnValue.error(function(response, status) {  
		   console.log("The request failed with response " + response + " and status code " + status);
		});


});// end controller 



//starting chart
rcMod.controller('Totalsmslast24hoursCtrl', function($scope, $http){

$scope.returnValue = $http.get("/restcomm/2012-04-24/Accounts/ACae6e420f425248d6a26948c17a9e2acf/Statistics/Totalsmslast24hours.json");  
		$scope.returnValue.success(function(data) {

	  		$scope.Totalsmslast24hours = data;
			//alert(data.totalsmslast24Hours);	

		});
		$scope.returnValue.error(function(response, status) {  
		   console.log("The request failed with response " + response + " and status code " + status);
		});


});// end controller 





//starting chart
rcMod.controller('toptencallersCtrl', function($scope, $http){


$scope.arrayValue =[];

$scope.returnValue = $http.get("/restcomm/2012-04-24/Accounts/ACae6e420f425248d6a26948c17a9e2acf/Statistics/Toptencallers.json");  
		$scope.returnValue.success(function(data) {
 
		angular.forEach(data, function(value,key){
			var myLabel = value.sender + " -> "+ value.recipient ;
			$scope.arrayValue[key]= {label : myLabel, value : parseInt(value.numberOfCalls)};

			});	

	  		//console.log(angular.toJson($scope.arrayValue));	

		});
		$scope.returnValue.error(function(response, status) {  
		   console.log("The request failed with response " + response + " and status code " + status);
		});


        /* Chart options */
$scope.options = {
    chart: {
        type: 'discreteBarChart',
        height: 300,
        margin : {
            top: 20,
            right: 20,
            bottom: 140,
            left: 55
        },
        x: function(d){ return d.label; },
        y: function(d){ return d.value; },
	staggerLabels: true,
        showValues: true,
        valueFormat: function(d){
            return d3.format(',')(d);
        },
        transitionDuration: 500,
        xAxis: {
            axisLabel: ''
        },
        yAxis: {
            axisLabel: 'Number of Calls',
            axisLabelDistance: 30
        }
    }
};//end options

        /* Chart data */
$scope.data = [{
    //key: "Cumulative Return",
    values: $scope.arrayValue
  }];


});// end controller myCtrl




//starting chart toptensmssenderCtrl

rcMod.controller('toptensmssenderCtrl', function($scope, $http){


$scope.arrayValue =[];

$scope.returnValue = $http.get("/restcomm/2012-04-24/Accounts/ACae6e420f425248d6a26948c17a9e2acf/Statistics/Toptensendersms.json");  
		$scope.returnValue.success(function(data) {
 
		angular.forEach(data, function(value,key){
			var myLabel = value.sender + " -> "+ value.recipient ;
			$scope.arrayValue[key]= {label : myLabel, value : parseInt(value.numberOfSMS)};

			});	

	  		//console.log(angular.toJson($scope.arrayValue));	

		});
		$scope.returnValue.error(function(response, status) {  
		   console.log("The request failed with response " + response + " and status code " + status);
		});


        /* Chart options */
$scope.options = {
    chart: {
        type: 'discreteBarChart',
        height: 300,
        margin : {
            top: 20,
            right: 20,
            bottom: 140,
            left: 55
        },
        x: function(d){ return d.label; },
        y: function(d){ return d.value; },
	staggerLabels: true,
        showValues: false,
        valueFormat: function(d){
             return d3.format(',.4f')(d);
        },
        transitionDuration: 500,
        xAxis: {
            axisLabel: ''
        },
        yAxis: {
            axisLabel: 'Number of SMS',
            axisLabelDistance: 30
        }
    }
};//end options

        /* Chart data */
$scope.data = [{
    //key: "Cumulative Return",
    values: $scope.arrayValue
  }];


});// end controller myCtrl



//starting chart toptensmssenderCtrl

rcMod.controller('Callsperdaylast30daysCtrl', function($scope, $http){


$scope.arrayValue =[];

$scope.returnValue = $http.get("/restcomm/2012-04-24/Accounts/ACae6e420f425248d6a26948c17a9e2acf/Statistics/Callsperdaylast30days.json");  
		$scope.returnValue.success(function(data) {
 
		angular.forEach(data, function(value,key){
			var myLabel = value.day + " - "+ value.month_name ;
			$scope.arrayValue[key]= {label : myLabel, value : parseInt(value.number_of_calls_per_day)};

			});	

	  		//console.log(angular.toJson($scope.arrayValue));	

		});
		$scope.returnValue.error(function(response, status) {  
		   console.log("The request failed with response " + response + " and status code " + status);
		});


        /* Chart options */
$scope.options = {
    chart: {
        type: 'discreteBarChart',
        height: 300,
        margin : {
            top: 20,
            right: 20,
            bottom: 120,
            left: 55
        },
        x: function(d){ return d.label; },
        y: function(d){ return d.value; },
        showValues: true,
        valueFormat: function(d){
            return d3.format(',')(d);
        },
        transitionDuration: 500,
        xAxis: {
            axisLabel: ''
        },
        yAxis: {
            axisLabel: 'Number of Calls',
            axisLabelDistance: 30
        }
    }
};//end options

        /* Chart data */
$scope.data = [{
    //key: "Cumulative Return",
    values: $scope.arrayValue
  }];


});// end controller myCtrl






//starting chart 

rcMod.controller('Smsperdaylast30daysCtrl', function($scope, $http){


$scope.arrayValue =[];

$scope.returnValue = $http.get("/restcomm/2012-04-24/Accounts/ACae6e420f425248d6a26948c17a9e2acf/Statistics/Smsperdaylast30days.json");  
		$scope.returnValue.success(function(data) {
 
		angular.forEach(data, function(value,key){
			var myLabel = value.day + " - "+ value.month_name ;
			$scope.arrayValue[key]= {label : myLabel, value : parseInt(value.number_of_sms_per_day)};

			});	

	  		//console.log(angular.toJson($scope.arrayValue));	

		});
		$scope.returnValue.error(function(response, status) {  
		   console.log("The request failed with response " + response + " and status code " + status);
		});


        /* Chart options */
$scope.options = {
    chart: {
        type: 'discreteBarChart',
        height: 300,
        margin : {
            top: 20,
            right: 20,
            bottom: 120,
            left: 55
        },
        x: function(d){ return d.label; },
        y: function(d){ return d.value; },
        showValues: true,
        valueFormat: function(d){
            return d3.format(',')(d);
        },
        transitionDuration: 500,
        xAxis: {
            axisLabel: ''
        },
        yAxis: {
            axisLabel: 'Number of SMS',
            axisLabelDistance: 30
        }
    }
};//end options

        /* Chart data */
$scope.data = [{
    //key: "Cumulative Return",
    values: $scope.arrayValue
  }];


});// end controller myCtrl



//starting chart 

rcMod.controller('Callspermonthlast12months', function($scope, $http){


$scope.arrayValue =[];

$scope.returnValue = $http.get("/restcomm/2012-04-24/Accounts/ACae6e420f425248d6a26948c17a9e2acf/Statistics/Callspermonthlast12months.json");  
		$scope.returnValue.success(function(data) {
 
		angular.forEach(data, function(value,key){
			var myLabel = value.month_name + " - "+ value.year ;
			$scope.arrayValue[key]= {label : myLabel, value : parseInt(value.number_of_calls_per_month)};

			});	

	  		//console.log(angular.toJson($scope.arrayValue));	

		});
		$scope.returnValue.error(function(response, status) {  
		   console.log("The request failed with response " + response + " and status code " + status);
		});


        /* Chart options */
$scope.options = {
    chart: {
        type: 'discreteBarChart',
        height: 300,
        margin : {
            top: 20,
            right: 20,
            bottom: 120,
            left: 55
        },
        x: function(d){ return d.label; },
        y: function(d){ return d.value; },
        showValues: true,
        valueFormat: function(d){
            return d3.format(',')(d);
        },
        transitionDuration: 500,
        xAxis: {
            axisLabel: ''
        },
        yAxis: {
            axisLabel: 'Number of Calls',
            axisLabelDistance: 30
        }
    }
};//end options

        /* Chart data */
$scope.data = [{
    //key: "Cumulative Return",
    values: $scope.arrayValue
  }];


});// end controller myCtrl


//starting chart 

rcMod.controller('Smspermonthlast12months', function($scope, $http){


$scope.arrayValue =[];

$scope.returnValue = $http.get("/restcomm/2012-04-24/Accounts/ACae6e420f425248d6a26948c17a9e2acf/Statistics/Smspermonthlast12months.json");  
		$scope.returnValue.success(function(data) {
 
		angular.forEach(data, function(value,key){
			var myLabel = value.month_name + " - "+ value.year ;
			$scope.arrayValue[key]= {label : myLabel, value : parseInt(value.number_of_sms_per_month)};

			});	

	  		//console.log(angular.toJson($scope.arrayValue));	

		});
		$scope.returnValue.error(function(response, status) {  
		   console.log("The request failed with response " + response + " and status code " + status);
		});


        /* Chart options */
$scope.options = {
    chart: {
        type: 'discreteBarChart',
        height: 300,
        margin : {
            top: 20,
            right: 20,
            bottom: 120,
            left: 55
        },
        x: function(d){ return d.label; },
        y: function(d){ return d.value; },
        showValues: true,
        valueFormat: function(d){
            return d3.format(',')(d);
        },
        transitionDuration: 500,
        xAxis: {
            axisLabel: ''
        },
        yAxis: {
            axisLabel: 'Number of SMS',
            axisLabelDistance: 30
        }
    }
};//end options

        /* Chart data */
$scope.data = [{
    //key: "Cumulative Return",
    values: $scope.arrayValue
  }];



});// end controller myCtrl











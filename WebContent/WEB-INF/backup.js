(function(){
	"use strict"
	
	angular.module('restApp').controller('MainCtrl',MainController);
	
	MainController.$inject=['$http','dataService'];
	function MainController(dataService,$http){
		var mctrl=this;
		mctrl.func={
				role: 'false'
		};
		mctrl.adminLogin=false;
		mctrl.pass=false;
		mctrl.done=false;
		mctrl.user="admin";
		mctrl.password="admin";
		mctrl.view=false;
		mctrl.showImageTables=false;
		
		$http({
			method: 'GET',
			url: 'api/employee/checkLogin',
			data: mctrl.auth			
		}).success(function(data){
			console.log(data);
			if(data.status == 'success'){
				mctrl.loggedIn=true;
				$location.path('postLogin.html');
			}else{
				mctrl.loggedIn=false;
			}
		}).error(function(error){
			console.log(error);
		});
		
		//login functionality for admin
		mctrl.tryLogin=function(){
			if(mctrl.login.user === mctrl.user ){
				if(mctrl.login.password === mctrl.password)
					{
					console.log("success");
				mctrl.adminLogin=false;
					mctrl.loggedIn=true;
					mctrl.message="success";
					}
				
			}
			else
				{
				mctrl.adminLogin=true;
				mctrl.loggedIn=false;
				mctrl.message="Wrong username/password";
				}
			/*$http({
				method: 'POST',
				url: 'api/employee/login',
				data: mctrl.auth			
			}).success(function(data){
				console.log(data);
				if(data.status == 'success'){
					mctrl.loggedIn=true;
					$location.path('postLogin.html');
				}else{
					mctrl.loggedIn=false;
				}
			}).error(function(error){
				console.log(error);
			});*/
			
		};
		mctrl.adminEdit=function(){
			mctrl.func.role='edit';
		};
		mctrl.adminCancel=function(){
			mctrl.func.role='cancel';
		};
		mctrl.adminSeat=function(){
			mctrl.func.role='seat';
		};
		mctrl.adminView=function(){
			mctrl.func.role='view';
		};
		
	dataService.getSeating().then(function(data){
		mctrl.people=data.payload;
	},function(error){
		console.log(error);
		
	});
		
		//function to see admin seating
		mctrl.showAdminSeating=function(){
			mctrl.showImageTables=true;
			$http({
				method: 'GET',
				url: 'api/employee/tables'
							
			}).success(function(data){
				console.log(data);
				mctrl.tables=data.payload;
			}).error(function(error){
				console.log(error);
			});
			
		};
		
		//function to get the list of all reservations
		mctrl.getAll=function(){
			mctrl.func.role='reserve';
			$http({
				method: 'GET',
				url: 'api/employee/all'
							
			}).success(function(data){
				console.log(data);
				mctrl.people=data.payload;
			}).error(function(error){
				console.log(error);
			});
			
		};
	
	
	//function to get specific reservation		
		mctrl.getRservation=function(){
			mctrl.go=true;
			if(mctrl.code){
				$http({
					method: 'GET',
					url: 'api/employee/get/'+mctrl.code
								
				}).success(function(data){
					mctrl.one=data.payload;
					console.log(data);
				}).error(function(error){
					console.log(error);
				});
			}
			
		};
		
		//function to cancel a reservation
		mctrl.cancelReservation=function(c){
			if(c){
				$http({
					method: 'DELETE',
					url: 'api/employee/cancel/'+c
								
				}).success(function(data){
					mctrl.cancel=null;
					console.log(data);
				}).error(function(error){
					console.log(error);
				});
			}
			
		};
		
		//admin's functionality to can cel reservation
		mctrl.adminCancel=function(cc,position){
			mctrl.people.splice(position,1);
			if(cc){
				$http({
					method: 'DELETE',
					url: 'api/employee/cancel/'+cc
								
				}).success(function(data){
					
					console.log(data);
				}).error(function(error){
					console.log(error);
				});
			}
		};
		
		//function to edit a reservation
		mctrl.editReservation=function(){
			//mctrl.view=false;
			if(mctrl.changeRecord.c){
				$http({
					method: 'PUT',
					url: 'api/employee/edit/'+mctrl.changeRecord.c
								
				}).success(function(data){
					mctrl.changeRecord=null;
					mctrl.done=true;
					console.log(data);
				}).error(function(error){
					console.log(error);
					mctrl.done=false;
				});
			}
			
		};
		
		//function to confirm a reservation by admin
		mctrl.adminConfirmation=function(){
			mctrl.showImageTables=false;
			
				$http({
					method: 'PUT',
					url: 'api/employee/confirm/',
					data: mctrl.confirm
								
				}).success(function(data){
					mctrl.changeRecord=null;
					mctrl.done=true;
					console.log(data);
				}).error(function(error){
					console.log(error);
					mctrl.done=false;
				});
			
			
		};
		
	
		//function to add a reservation
		mctrl.addRecord=function(){
			$http({
				method: 'POST',
				url: 'api/employee/add',
				data: mctrl.newRecord
			}).success(function(data){
				mctrl.dataStaus=data.status;
				mctrl.dataCode=data.payload;
				mctrl.pass=true;
				console.log(data);
				mctrl.newRecord=null;
				//return data;
			}).error(function(error){
				mctrl.pass=false;
				console.log(error);
				mctrl.newRecord=null;
			});
		};
		};
	
	}	
)();


/******************************************************************************************************************************************


<!-- nav class="navbar navbar-default navbar-fixed-top" role="navigation">

    <div class="navbar-header">

        <button type="button" class="navbar-collapse collapse"  ng-click="mctrl.adminLogin=true">
            <span>Admin</span>
            
        </button>
    </div>
    
    <nav class="navbar navbar-default" ng-show="mctrl.loggedIn">
    <div class="container">
      <ul class="nav navbar-nav navbar-left">
        <button type="button" class="btn btn-lg btn-default" data-ng-click="mctrl.getAll()" >View Reservations</button>
        
        <button type="button" class="btn btn-lg btn-default" ng-click="mctrl.adminEdit()"  >Edit Reservations</button>
        <button type="button" class="btn btn-lg btn-default" >Auto-assign Reservations</button>
        <button type="button" class="btn btn-lg btn-default" ng-click="mctrl.adminSeat()">View Seating</button>
        <div class="col-md-8" data-ng-show="mctrl.func.role === 'reserve'" ng-include="'grid.html'" ></div>
        <div class="col-md-8" data-ng-show="mctrl.func.role === 'edit'" ng-include="'edit.html'"></div>
        <form name="iterable_optin" data-ng-show="mctrl.func.role === 'cancel'" data-ng-submit="mctrl.cancelReservation(mctrl.cancel.code)">
        <div class="input_container">
            <input id="iterable_email" class="input_email" type="text" placeholder="confirmation code" data-ng-model="mctrl.cancel.code" >
            
        </div>
        <input class="button" type="submit" value="Cancel" >
    </form>
    <div class="col-md-8" data-ng-show="mctrl.func.role === 'seat'" ng-include="'seatGrid.html'" ></div>
      </ul>
    </div>
  </nav>
</nav-->*/
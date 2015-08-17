(function(){
	"use strict"
	
	angular.module('restApp')
	.controller('MainCtrl',MainController);
	
		
	MainController.$inject=['dataService','$http','$routeParams'];
	function MainController(dataService,$http,$routeParams){
		var mctrl=this;
		mctrl.func={
				role: 'false'
		};
		mctrl.det=null;
		mctrl.adminLogin=false;
		mctrl.pass=false;
		mctrl.done=false;
		mctrl.user="admin";
		mctrl.password="admin";
		mctrl.view=false;
		mctrl.showImageTables=false;
		mctrl.adminLabel="Admin Login";
		mctrl.loggedIn=false;
		console.log($routeParams);
		
		mctrl.toggle=function(){
			if(mctrl.loggedIn){
				mctrl.adminLogin=false;
				mctrl.adminLabel="Admin Login";
				mctrl.loggedIn=false;
				
			}
			else{
				mctrl.adminLogin=true;
				mctrl.adminLabel="Logout";
			}
		};
		/*dataService.getReservation($routeParams.code).then(function(data){
			mctrl.det=data.payload;
			console.log(mctrl.det);
		},function(error){
			console.log(error);
		});*/
		
		mctrl.getReservation=function(code){
			console.log(code);
			$http({
				method: 'GET',
				url: 'api/employee/get/'+code
							
			}).success(function(data){
				mctrl.det=data.payload;
				console.log(data);
				
			}).error(function(error){
				console.log(error);
			});
		};
		
			
				
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
					mctrl.adminLabel="Logout";
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
		mctrl.showImageTables=true;
		mctrl.people=data.payload;
	},function(error){
		console.log(error);
		
	});
	
	dataService.showAdminSeating().then(function(data){
		console.log(data);
		mctrl.tables=data.payload;
	},function(error){
		console.log(error);
	});
	
	
		
	/*$http({
		method: 'GET',
		url: 'api/employee/all'
					
	}).success(function(data){
		console.log(data);
		mctrl.people=data.payload;
	}).error(function(error){
		console.log(error);
	});*/
		
		/*mctrl.showAdminSeating=function(){
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
			
		};*/
		/*
		 * mctrl.addRecord=function(){
						
			$http({
				method: 'POST',
				url: 'api/employee/add',
				data: mctrl.newRecord
							
			}).success(function(data){
				
				defer.resolve(data);
				
			}).error(function(error){
				defer.reject(error);
			});
			return defer.promise;
			
		};
		 * 
		 * 
		 * 
		 * */
		//function to get the list of all reservations
	
	dataService.getAll().then(function(data){
		mctrl.people=data.payload;
		console.log(mctrl.people);
	},function(error){
		console.log(error);
	});
			
		
		
	//function to get specific reservation		
		/*mctrl.getReservation=function(reserveid){
			//mctrl.go=true;
			if(p.code){
				$http({
					method: 'GET',
					url: 'api/employee/get/'+p.code
								
				}).success(function(data){
					mctrl.one=data.payload;
					console.log(data);
				}).error(function(error){
					console.log(error);
				});
			}
			
		};*/
		
		//function to cancel a reservation
	
	/*dataService.cancelReservation(mctrl.cancel.code).then(function(data){
		mctrl.cancel=null;
		console.log(data);
	},function(error){
		console.log(error);
		
	});*/
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
		
	/*	dataService.editReservation().then(function(data){
			mctrl.changeRecord=null;
			mctrl.done=true;
			console.log(data);
		},function(error){
			console.log(error);
			mctrl.done=false;
		});*/
		
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
		mctrl.adminConfirmation=function(code,table){
			mctrl.showImageTables=false;
			mctrl.confirm.code=code;
			mctrl.confirm.table_id=table;
			console.log(mctrl.confirm);
			
				$http({
					method: 'PUT',
					url: 'api/employee/confirm/',
					data: mctrl.confirm
								
				}).success(function(data){
					//mctrl.changeRecord=null;
					//mctrl.done=true;
					console.log(data);
				}).error(function(error){
					console.log(error);
					//mctrl.done=false;
				});
			
			
		};
		
	/*
		dataService.addRecord().then(function(data){
			mctrl.dataStaus=data.status;
			mctrl.dataCode=data.payload;
			mctrl.pass=true;
			console.log(data);
			mctrl.newRecord=null;
		},function(error){
			mctrl.pass=false;
			console.log(error);
			mctrl.newRecord=null;
		});*/
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
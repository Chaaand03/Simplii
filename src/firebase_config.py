import pyrebase

firebase_config = {
    "apiKey": "AIzaSyDtmIIfDSz13PXA-QpgsWjJflxPjFCd_4g",
    "authDomain": "simplii-7eeb5.firebaseapp.com",
    "projectId": "simplii-7eeb5",
    "storageBucket": "simplii-7eeb5.appspot.com",
    "messagingSenderId": "272974507960",
    "appId": "1:272974507960:web:8202797d0a23c081f4294d",
    "measurementId": "G-H9F327ZD63",
    "databaseURL": ""
}

firebase = pyrebase.initialize_app(firebase_config)
auth = firebase.auth()

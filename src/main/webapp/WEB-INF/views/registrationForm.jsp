<div id="mainContentBubble" class = "rounded_menu txt_lightgrey" style="width:51em;">
		<form id="theform" method="post" action="signup">
			<fieldset id="pt1">
				<legend><span>Step </span>1. <span>: Login details</span></legend>
				<h3>Pick a login name.</h3>
				<div class="help">Your login will be username.</div>
		
				<label for="loginname">Username</label>
				<input type="text" id="loginname" name="username" tabindex="1" />
			</fieldset>
			<fieldset id="pt2">
				<legend><span>Step </span>2. <span>: Password</span></legend>
				<h3>Choose a password for your new account.</h3>
		
				<div class="help">Passwords must be 6-30 characters in length.</div>
				<label for="password1">Password</label>
				<input type="password" id="password1" name="pword1" tabindex="2" />
				<label for="password2">Repeat Password</label>
				<input type="password" id="password2" name="pword2" tabindex="3" />
			</fieldset>
			<fieldset id="pt3" class="error">
		
				<legend><span>Step </span>3. <span>: Email details</span></legend>
				<h3>Enter your email address.</h3>
				<div class="help">You must enter a valid email address to activate your account.</div>
				
				<label for="email1">Email</label>
		
				<input type="text" id="email1" name="email1" tabindex="4" />
				<label for="email2">Repeat Email</label>
				<input type="text" id="email2" name="email2" tabindex="5" />
			</fieldset>
			<fieldset id="pt4">
				<legend>Step 4  : Submit form</legend>
				<h3>Terms of Service</h3>
		
				<div id="disclaimer">After clicking the &#8220;Complete Signup&#8221; button,
					you will be re-directed to the login page and you will be able to instantly
					logon to access the site.</div>
				<input type="submit" id="submitform" tabindex="6" value="Complete Signup &raquo;" />
			</fieldset>
			<div id="copyright"></div>
		</form>
</div>
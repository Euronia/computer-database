$(function() {
  // Initialize form validation on the addComputer form.
  // It has the name attribute "addComputer"
  $('#addComputer').validate({
	errorClass: "my-error-class",
    // Specify validation rules
    rules: {
      // The key name on the left side is the name attribute
      // of an input field. Validation rules are defined
      // on the right side
      name: {required : true, minlength: 2},
      introduced: {required : false},
      discontinued: {required : false}
    },
    // Specify validation error messages
    messages: {
      name: { required : "<li>Please enter a name</li>",
    	  	  minlength : "<li>Name must be longer than 1 character</li>"}
    }
  });
});
$(document).ready(function() {
	'use strict';

	$('#form').submit(function(e) {

		e.preventDefault();

		var form = $(this);
		form.attr('action', form.find('#methodUrl').val());
		
		$.ajax({
			type	: 'POST',
			cache	: false,
			url		: form.attr('action'),
			data	: form.serialize(),
			success	: function(data) {
				$("html").html(data);
			}
		});
	});

	$('#entries').find('.entryDelete').on('submit', function(e){

		e.preventDefault();

		var form = $(this);
		var id = form.attr('data-entry-id');

		$.ajax({
			type	: 'GET',
			cache	: false,
			url		: form.attr('action'),
			data	: form.serialize(),
			success	: function() {

				$('#entry' + id).slideUp(500, function() {
					var followingEntries = $(this).parent().nextAll().each(function() {
						var textArray = $(this).find('h4').text().split('.', 2);
						$(this).find('h4').text((parseInt(textArray[0],10)-1) + '.' + textArray[1]);
					});

					$(this).parent().remove();
				});
			}
		});
	});
	
	$('#entries').find('.entryApprove').on('submit', function(e){

		e.preventDefault();

		var form = $(this);
		var id = form.attr('data-entry-id');

		$.ajax({
			type	: 'GET',
			cache	: false,
			url		: form.attr('action'),
			data	: form.serialize(),
			success	: function() {
				
				$("#entryApprove"+id).remove();
				
			}
		});
	});
});
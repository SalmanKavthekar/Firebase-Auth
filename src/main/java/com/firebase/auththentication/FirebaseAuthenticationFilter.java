package com.firebase.auththentication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

	private final AuthenticationManager authenticationManager;

	public FirebaseAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
							HttpServletResponse response, 
							FilterChain filterChain)
									throws ServletException, IOException {
		// Extract Firebase ID token from the request
		String idToken = extractIdToken(request);

		if (idToken != null) {
			try {
				// Verify and parse the Firebase ID token
				FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
				String uid = decodedToken.getUid();

				// Create an authentication token and set up the Spring Security context
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(uid, null, null);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (Exception e) {
				// Handle token verification error
			}
		}

		filterChain.doFilter(request, response);
	}

	public static String extractIdToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		}

		return null;
	}

}

package io.jzheaux.springsecurity.resolutions;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

@Component
public class ResolutionInitializer implements SmartInitializingSingleton {
	private final ResolutionRepository resolutions;

	private final UserRepository userRepository;

	public ResolutionInitializer(ResolutionRepository resolutions, UserRepository userRepository) {
		this.resolutions = resolutions;
		this.userRepository = userRepository;
	}

	@Override
	public void afterSingletonsInstantiated() {
		this.resolutions.save(new Resolution("Read War and Peace", "user"));
		this.resolutions.save(new Resolution("Free Solo the Eiffel Tower", "user"));
		this.resolutions.save(new Resolution("Hang Christmas Lights", "user"));

		User admin = new User("admin", "{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVG5tHQ08W");
		admin.setFullName("Admin Adminson");
		admin.grantAuthority("ROLE_ADMIN");
		admin.grantAuthority("user:read");
		this.userRepository.save(admin);

		User user = new User("user",
				"{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVG5tHQ08W");
		user.setFullName("User Userson");
		user.grantAuthority("resolution:read");
		user.grantAuthority("user:read");
		user.grantAuthority("resolution:write");
		this.userRepository.save(user);

		User hasread = new User("user",
				"{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVG5tHQ08W");
		hasread.setFullName("Has Read");
		hasread.grantAuthority("resolution:read");
		hasread.grantAuthority("user:read");
		this.userRepository.save(hasread);

		User haswrite = new User("user",
				"{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVG5tHQ08W");
		haswrite.setFullName("Has Write");
		haswrite.grantAuthority("resolution:write");
		this.userRepository.save(haswrite);
	}
}

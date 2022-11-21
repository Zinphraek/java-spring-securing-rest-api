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

		User user = new User("user",
				"{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVG5tHQ08W");
		user.grantAuthority("resolution:read");
		user.grantAuthority("resolution:write");
		this.userRepository.save(user);

		User hasread = new User("user",
				"{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVG5tHQ08W");
		hasread.grantAuthority("resolution:read");
		this.userRepository.save(hasread);

		User haswrite = new User("user",
				"{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVG5tHQ08W");
		haswrite.grantAuthority("resolution:write");
		this.userRepository.save(haswrite);
	}
}

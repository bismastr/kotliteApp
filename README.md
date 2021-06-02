<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Thanks again! Now go create something AMAZING! :D
***
***
***
*** To avoid retyping too much info. Do a search and replace for the following:
*** github_username, repo_name, twitter_handle, email, project_title, project_description
-->

<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="#">
    <img src="assets/kotlite_logo.png" alt="kotliteLogo" height="150">
  </a>

  <p align="center">
    <img src="https://img.shields.io/badge/Team-Brillante-9e83fc">
    <img src="https://img.shields.io/badge/ID-BA21_CAP0176-9e83fc?">
  </p>

  <h3 align="center">Kotlite (Angkot Elite) Android Apps</h3>

  <p align="center">
    A Part of Kotlite Ridesharing Application
    <br />
    <a href="https://github.com/github_username/repo_name"><strong>Explore the Projects »</strong></a>
    <br />
    <br />
    <a href="https://github.com/github_username/repo_name">APK Demo</a>
    ·
    <a href="https://github.com/bismastr/kotliteApp/issues">Report Bug</a>
    ·
    <a href="https://github.com/bismastr/kotliteApp/issues">Request Feature</a>
  </p>
</p>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

<!-- [![Product Name Screen Shot][product-screenshot]](https://example.com) -->

<!-- Here's a blank template to get started:
**To avoid retyping too much info. Do a search and replace with your text editor for the following:**
`github_username`, `repo_name`, `twitter_handle`, `email`, `project_title`, `project_description` -->

Kotlite is here as a breakthrough idea that answers the need of users to find drivers or passengers from and going to the same routes. It is designed to optimistically accelerate the transformation of the citizens' lifestyle to rideshare and network, eliminating incurable rush hour traffic and provide practicality for user's mobility from one place to another.

### Built With

- [Kotlin](https://developer.android.com/kotlin)
- [Kotlite RestAPI](https://github.com/SVeeIS/kotliteProjectAPI)
- [Material Design](https://material.io/develop/android/docs/getting-started)
- [Miro Workflow](https://miro.com/app/board/o9J_lG_2mC8=/)

<!-- GETTING STARTED -->

## Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

This project requires several resources to be prepared and installed on the local computer, including:

- [Android Studio](https://www.python.org/downloads/)
- [Google Maps SDK](https://developers.google.com/maps/documentation/android-sdk/overview)
- [Google Maps API Key](https://developers.google.com/maps)

### Installation Project

1. Clone the repository

   ```sh
   git clone https://github.com/bismastr/kotliteApp.git
   cd kotliteProjectAPI
   ```

2. Setup your Google Maps API Environment

   - Add your Google Maps API Key at `Local Properties`
     ```kotlin
     MAPS_API_KEY = [Your Api Key]
     ```
   - Add this line in your gradle to hide your API and don't forget to sync

     ```kotlin
     id("com.google.secrets_gradle_plugin") version "0.6"
     ```

   - Add your Maps API Key in `android manifest`
     ```kotlin
     <meta-data
       android:name="com.google.android.geo.API_KEY"
       android:value="${MAPS_API_KEY}" />
     ```

3. Run the apps

   - You can run kotlite apps in an emulator or on-device
   - Being able to run the apps and sign-in means the whole setup is works

<!-- USAGE EXAMPLES -->

## Application

**Nanti ada architecture Review**

This is the place to explain from the architecture review image, for example [here](https://github.com/hypertrack/ridesharing-android#architecture-review).

### Minimum Requirements

- Android 5.0 (lollipop) or newest
- Internet Connection
- Location Services

### Feature

1. Driver Features

   - Doing travel scheduling
   - View the list of passengers who made a booking
   - View passenger details
   - Knowing the direction of each passenger who makes a booking
   - Invoice

2. Passenger Feature
   - Get recommendations for drivers who have similar trips
   - Doing travel scheduling
   - Driver status notification
   - Invoice

### Usage of Apps

**DISINI TEMPAT TUTORIALNYA CARA PAKAI APPSNYA**

<!-- CONTRIBUTING -->

## Contributing

Feel free to clone, use, and contribute back via [pull request](https://docs.github.com/en/github/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/about-pull-requests). We'd love to see your pull requests and sent them in! Please use the [issues tracker](https://github.com/bismastr/kotliteApp/issues) to raise bug reports and feature request.

We are excited to see what amazing feature you build in your application using this project. Do ping us at our [contact](#contact) once you build one, feel free to contact us, and we would love to feature your app on our blog.

<!-- LICENSE -->

## License

Distributed under the MIT License. See `LICENSE.md` for more information.

<!-- CONTACT -->

## Contact

How can we help you? While we're occupied for the Capstone Project, there are simpler ways for us to get in touch! Please do visit us at

<!-- ACKNOWLEDGEMENTS -->

## Acknowledgements

- [Bangkit Academy 2021]()
- []()
- []()

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/bismastr/kotliteApp.svg?style=flat
[contributors-url]: https://github.com/bismastr/kotliteApp/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/bismastr/kotliteApp.svg?style=flat
[forks-url]: https://github.com/bismastr/kotliteApp/network/members
[issues-shield]: https://img.shields.io/github/issues/bismastr/kotliteApp.svg?style=flat
[issues-url]: https://github.com/bismastr/kotliteApp/issues
[license-shield]: https://img.shields.io/github/license/bismastr/kotliteApp.svg?style=flat
[license-url]: https://github.com/bismastr/kotliteApp/blob/master/LICENSE.txt

<!-- https://github.com/SVeeIS/kotliteProjectAPI -->

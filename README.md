<h1>FireConnect Kotlin MVVM</h1>
<h2><strong>Project Description</strong></h2>
<p><strong>FireConnect Kotlin MVVM</strong> was developed with the goal of consolidating the main Firebase functionalities for Android into a single project. This allows for future projects to reuse the code, eliminating the need to rewrite features that have already been implemented, saving time and effort.</p>
<p>This project follows the <strong>Model-View-ViewModel (MVVM)</strong> architecture pattern to ensure high code reuse and modularity. While the initial intention was to include all Firebase functionalities, this version supports:</p>
<ul>
<li><strong>Firebase Storage</strong></li>
<li><strong>Firestore</strong></li>
<li><strong>Realtime Database</strong></li>
</ul>
<blockquote>
<p><strong>Note:</strong> During development, I chose to push the complete project to GitHub rather than perform incremental commits, resulting in a single comprehensive commit. This version does not include unit tests.</p>
</blockquote>
<p>The choice of <strong>Kotlin</strong> over Java was made based on Google's official recommendation for Android development.</p>
<p>&nbsp;</p>
<h2><strong>Technologies Used</strong></h2>
<ul>
<li><strong>Kotlin</strong>: Main language of the project.</li>
<li><strong>Firebase SDK</strong>:
<ul>
<li>Firebase Storage</li>
<li>Firebase Firestore</li>
<li>Firebase Realtime Database</li>
<li>Firebase Authentication</li>
</ul>
</li>
<li><strong>Additional Libraries</strong>:
<ul>
<li><strong>Glide</strong>: For image loading.</li>
<li><strong>MaterialSearchView</strong>: For searching within the interface.</li>
<li><strong>AndroidX Lifecycle ViewModel</strong>: To implement the MVVM pattern.</li>
<li><strong>AndroidX Activity KTX</strong>: To simplify operations in activities.</li>
</ul>
</li>
</ul>
<p>&nbsp;</p>
<h2><strong>Features</strong></h2>
<p>The app provides direct integration with Firebase services:</p>
<ul>
<li><strong>Firebase Storage</strong>: Upload, download, and manage files.</li>
<li><strong>Firestore</strong>: NoSQL database for storing and syncing structured data.</li>
<li><strong>Realtime Database</strong>: Real-time synchronization for instant updates across connected clients.</li>
</ul>
<p>&nbsp;</p>
<h2><strong>Setup Instructions</strong></h2>
<h3><strong>Prerequisites</strong></h3>
<p>Ensure you have the following configured before running the project:</p>
<ol>
<li><strong>Android Studio</strong> installed.
<blockquote>
<p><em>(The version used during development is unspecified)</em>.</p>
</blockquote>
</li>
<li><strong>Firebase project configured</strong>.</li>
<li>The <code>google-services.json</code> file added to the <code>app</code> directory.</li>
</ol>
<h3>&nbsp;</h3>
<h3><strong>Running the Application</strong></h3>
<ol>
<li>Clone this repository:
<div class="contain-inline-size rounded-md border-[0.5px] border-token-border-medium relative bg-token-sidebar-surface-primary dark:bg-gray-950">
<div class="flex items-center text-token-text-secondary px-4 py-2 text-xs font-sans justify-between rounded-t-md h-9 bg-token-sidebar-surface-primary dark:bg-token-main-surface-secondary select-none">

<pre>
<code>git clone https://github.com/calbertobarbosajr/FireConnectKotlinMVVM.git</code>
</pre>

</div>

<ol>
<li>Open the project in <strong>Android Studio</strong>.</li>
<li>Sync the Gradle dependencies.</li>
<li>Build and run the app on an emulator or physical device.</li>
</ol>
<h3><strong>Configuring Firebase</strong></h3>
<ol>
<li>Create a project in the <a href="https://console.firebase.google.com" target="_new" rel="noopener">Firebase Console</a>.</li>
<li>Add an Android app to your Firebase project and download the <code>google-services.json</code> file.</li>
<li>Place the <code>google-services.json</code> file in the <code>app</code> module of your Android project.</li>
</ol>
<h3><strong>Project Dependencies</strong></h3>
<p>In the <code>build.gradle</code> (Module) file:</p>

<pre>
<code>
plugins {
    id("com.google.gms.google-services") version "4.4.0" apply false
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("io.gitlab.alexto9090:materialsearchview:1.0.0")
    implementation("androidx.activity:activity-ktx:1.8.2")
}

</code>
</pre>

<ol>
<li>In the <code>build.gradle</code> (Project) file:</li>
</ol>

<p>&nbsp;</p>
<h2><strong>Usage</strong></h2>
<p>The app provides a simple interface to:</p>
<ul>
<li>Upload files to Firebase Storage.</li>
<li>Interact with Firestore to store and query structured data.</li>
<li>Experience real-time updates with Firebase Realtime Database.</li>
</ul>

<p>&nbsp;</p>
<h2><strong>Contributions</strong></h2>
<p>Contributions are welcome! If you find issues, suggest improvements, or wish to collaborate, feel free to open <strong>Issues</strong> or submit <strong>Pull Requests</strong>.</p>

<p>&nbsp;</p>
<h2><strong>License</strong></h2>
<p>This project is licensed under the <strong>MIT License</strong>. Please refer to the full license text in <a href="https://www.mit.edu/~amini/LICENSE.md" target="_new" rel="noopener">LICENSE.md</a>.</p>

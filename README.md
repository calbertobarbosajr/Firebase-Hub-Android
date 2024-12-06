<h1>FireConnect Kotlin MVVM</h1>
<h2><strong>Descri&ccedil;&atilde;o do Projeto</strong></h2>
<p><strong>FireConnect Kotlin MVVM</strong> foi desenvolvido com o objetivo de consolidar as principais funcionalidades do Firebase para Android em um &uacute;nico projeto. Isso permite que futuros projetos reutilizem c&oacute;digo, eliminando a necessidade de reescrever funcionalidades j&aacute; implementadas, economizando tempo e esfor&ccedil;o.</p>
<p>Este projeto adota o padr&atilde;o de arquitetura <strong>Model-View-ViewModel (MVVM)</strong> para garantir alta reutiliza&ccedil;&atilde;o e modularidade do c&oacute;digo. Apesar da inten&ccedil;&atilde;o inicial de abranger todas as funcionalidades do Firebase, esta vers&atilde;o inclui suporte para:</p>
<ul>
<li><strong>Firebase Storage</strong></li>
<li><strong>Firestore</strong></li>
<li><strong>Realtime Database</strong></li>
</ul>
<blockquote>
<p><strong>Nota:</strong> Durante o desenvolvimento, optei por enviar o projeto completo para o GitHub em vez de realizar commits incrementais, resultando em um &uacute;nico commit abrangente. Esta vers&atilde;o n&atilde;o inclui testes unit&aacute;rios.</p>
</blockquote>
<p>A escolha pela linguagem <strong>Kotlin</strong>, em vez de Java, foi feita com base na recomenda&ccedil;&atilde;o oficial do Google para o desenvolvimento Android.</p>
<p>&nbsp;</p>
<h2><strong>Tecnologias Utilizadas</strong></h2>
<ul>
<li><strong>Kotlin</strong>: Linguagem principal do projeto.</li>
<li><strong>Firebase SDK</strong>:
<ul>
<li>Firebase Storage</li>
<li>Firebase Firestore</li>
<li>Firebase Realtime Database</li>
<li>Firebase Authentication</li>
</ul>
</li>
<li><strong>Bibliotecas adicionais</strong>:
<ul>
<li><strong>Glide</strong>: Para carregamento de imagens.</li>
<li><strong>MaterialSearchView</strong>: Para busca na interface.</li>
<li><strong>AndroidX Lifecycle ViewModel</strong>: Para implementa&ccedil;&atilde;o do padr&atilde;o MVVM.</li>
<li><strong>AndroidX Activity KTX</strong>: Para simplificar opera&ccedil;&otilde;es em atividades.</li>
</ul>
</li>
</ul>
<p>&nbsp;</p>
<h2><strong>Funcionalidades</strong></h2>
<p>O aplicativo oferece integra&ccedil;&atilde;o direta com os servi&ccedil;os Firebase:</p>
<ul>
<li><strong>Firebase Storage</strong>: Upload, download e gerenciamento de arquivos.</li>
<li><strong>Firestore</strong>: Banco de dados NoSQL para armazenamento e sincroniza&ccedil;&atilde;o de dados estruturados.</li>
<li><strong>Realtime Database</strong>: Sincroniza&ccedil;&atilde;o em tempo real para atualiza&ccedil;&otilde;es instant&acirc;neas em clientes conectados.</li>
</ul>
<p>&nbsp;</p>
<h2><strong>Passo a Passo de Execu&ccedil;&atilde;o</strong></h2>
<h3><strong>Pr&eacute;-requisitos</strong></h3>
<p>Certifique-se de ter os seguintes itens configurados antes de executar o projeto:</p>
<ol>
<li><strong>Android Studio</strong> instalado.
<blockquote>
<p><em>(Vers&atilde;o utilizada no desenvolvimento n&atilde;o especificada)</em>.</p>
</blockquote>
</li>
<li><strong>Projeto Firebase configurado</strong>.</li>
<li>Arquivo <code>google-services.json</code> adicionado ao diret&oacute;rio <code>app</code>.</li>
</ol>
<h3><strong>Executando o Aplicativo</strong></h3>
<ol>
<li>Clone este reposit&oacute;rio</li>
</ol>
<p><code><a href="https://github.com/calbertobarbosajr/Firebase-Hub-Android.git">git clone https://github.com/calbertobarbosajr/FireConnectKotlinMVVM.git</a></code></p>
<p>&nbsp;</p>
<ol>
<li>Abra o projeto no <strong>Android Studio</strong>.</li>
<li>Sincronize as depend&ecirc;ncias no Gradle.</li>
<li>Compile e execute o aplicativo em um emulador ou dispositivo f&iacute;sico.</li>
</ol>
<h3><strong>Configura&ccedil;&atilde;o do Firebase</strong></h3>
<ol>
<li>Crie um projeto no <a href="https://console.firebase.google.com" target="_new" rel="noopener">Console do Firebase</a>.</li>
<li>Adicione um aplicativo Android ao projeto Firebase e fa&ccedil;a o download do arquivo <code>google-services.json</code>.</li>
<li>Coloque o arquivo <code>google-services.json</code> no m&oacute;dulo <code>app</code> do seu projeto Android.</li>
</ol>
<h3><strong>Depend&ecirc;ncias do Projeto</strong></h3>
<p>No arquivo <code>build.gradle</code> (Module):</p>
<p>&nbsp;</p>
<p><code>plugins {</code><br /><code>id("com.google.gms.google-services") version "4.4.0" apply false</code><br /><code>}</code></p>
<p><code>dependencies {</code><br /><code>implementation(platform("com.google.firebase:firebase-bom:32.5.0"))</code><br /><code>implementation("com.google.android.gms:play-services-auth:20.7.0")</code><br /><code>implementation("com.google.firebase:firebase-auth-ktx")</code><br /><code>implementation("com.google.firebase:firebase-database-ktx")</code><br /><code>implementation("com.google.firebase:firebase-storage-ktx")</code><br /><code>implementation("com.google.firebase:firebase-firestore-ktx")</code><br /><code>implementation("com.github.bumptech.glide:glide:4.14.2")</code><br /><code>annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")</code><br /><code>implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")</code><br /><code>implementation("io.gitlab.alexto9090:materialsearchview:1.0.0")</code><br /><code>implementation("androidx.activity:activity-ktx:1.8.2")</code><br /><code>}</code></p>
<p>No arquivo <code>build.gradle</code> (Project):</p>
<p>&nbsp;</p>
<p><code>plugins {</code><br /><code>id("com.google.gms.google-services")</code><br /><code>}</code></p>
<p><code>android {</code><br /><code>viewBinding { enable = true }</code><br /><code>}</code></p>
<p><code></code></p>
<h2><strong>Uso</strong></h2>
<p>O aplicativo oferece uma interface intuitiva para:</p>
<ul>
<li>Fazer upload de arquivos para o Firebase Storage.</li>
<li>Interagir com Firestore para armazenar e consultar dados estruturados.</li>
<li>Observar atualiza&ccedil;&otilde;es em tempo real com o Firebase Realtime Database.</li>
</ul>
<p>&nbsp;</p>
<h2><strong>Contribui&ccedil;&otilde;es</strong></h2>
<p>Contribui&ccedil;&otilde;es s&atilde;o bem-vindas! Se voc&ecirc; encontrar problemas, propor melhorias ou desejar colaborar, sinta-se &agrave; vontade para abrir <strong>Issues</strong> ou enviar <strong>Pull Requests</strong>.</p>
<p>&nbsp;</p>
<h2><strong>Licen&ccedil;a</strong></h2>
<p>Este projeto est&aacute; licenciado sob a <strong>Licen&ccedil;a MIT</strong>. Consulte o texto completo da licen&ccedil;a em <a href="https://www.mit.edu/~amini/LICENSE.md" target="_new" rel="noopener">LICENSE.md</a>.</p>
<p><code></code></p>

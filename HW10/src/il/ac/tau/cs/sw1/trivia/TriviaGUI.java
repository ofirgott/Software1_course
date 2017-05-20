package il.ac.tau.cs.sw1.trivia;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TriviaGUI {

	private static final int MAX_ERRORS = 3;
	private Shell shell;
	private Label scoreLabel;
	private Composite questionPanel;
	private Font boldFont;
	private String lastAnswer = "";
	private String filePath;
	private Button[] buttons_array;
	private Button passButton;

	public void open() {
		createShell();
		runApplication();
	}

	/**
	 * Creates the widgets of the application main window
	 */
	private void createShell() {
		Display display = Display.getDefault();
		shell = new Shell(display);
		shell.setText("Trivia");

		// window style
		Rectangle monitor_bounds = shell.getMonitor().getBounds();
		shell.setSize(new Point(monitor_bounds.width / 3,
				monitor_bounds.height / 4));
		shell.setLayout(new GridLayout());

		FontData fontData = new FontData();
		fontData.setStyle(SWT.BOLD);
		boldFont = new Font(shell.getDisplay(), fontData);

		// create window panels
		createFileLoadingPanel();
		createScorePanel();
		createQuestionPanel();
	}

	/**
	 * Creates the widgets of the form for trivia file selection
	 */
	private void createFileLoadingPanel() {
		final Composite fileSelection = new Composite(shell, SWT.NULL);
		fileSelection.setLayoutData(GUIUtils.createFillGridData(1));
		fileSelection.setLayout(new GridLayout(4, false));

		final Label label = new Label(fileSelection, SWT.NONE);
		label.setText("Enter trivia file path: ");

		// text field to enter the file path
		final Text filePathField = new Text(fileSelection, SWT.SINGLE
				| SWT.BORDER);
		filePathField.setLayoutData(GUIUtils.createFillGridData(1));

		// "Browse" button
		final Button browseButton = new Button(fileSelection, SWT.PUSH);
		browseButton.setText("Browse");

		browseButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				filePath = GUIUtils.getFilePathFromFileDialog(shell);
				filePathField.setText(filePath);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		// "Play!" button
		final Button playButton = new Button(fileSelection, SWT.PUSH);
		playButton.setText("Play!");
		playButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (e.getSource() instanceof Button) {
						Game game = new Game();
						questionsLoading(game);
						startGame(game);
						gameProccess(game);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void questionsLoading(Game game) throws IOException {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					filePath));
			String line;
			int questions_cnt = 0;
			while ((line = bufferedReader.readLine()) != null) { // read data
																	// from file
				String[] line_arr = line.split("\t");
				String question = line_arr[0];
				List<String> answers = new ArrayList<String>();
				for (int i = 1; i < line_arr.length; i++) {
					answers.add(line_arr[i]);
				}
				Collections.shuffle(answers);
				TriviaQuestion Q_A = new TriviaQuestion(question, answers,
						line_arr[1]);
				game.questionsList.add(Q_A);
				questions_cnt++;
			}
			bufferedReader.close();
			Collections.shuffle(game.questionsList);
			game.setQuestions_cnt(questions_cnt);

		} catch (FileNotFoundException e1) {
			GUIUtils.showErrorDialog(shell, "Error with file");
			e1.printStackTrace();
		}

	}

	private void startGame(Game game) throws IOException {

		scoreLabel.setText(Integer.toString(game.getScore()));
		try {
			if (game.questionsList.size() == 0) { // check if there are
													// questions

				throw new InputMismatchException();

			} else { // there is at least 1 questions to show

				updateQuestionPanel(game, game.questionsList.get(0)
						.getQuestion(), game.questionsList.get(0).getAnswers());

			}

		} catch (InputMismatchException e) {
			GUIUtils.showErrorDialog(shell,
					"Trivia file foramt error: Trivia file is empty.");
		}

	}

	private void gameProccess(Game game) {

		// answer buttons listeners
		for (Button answerButton : buttons_array) {
			answerButton.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					String correct;
					if (e.getSource() instanceof Button
							&& game.getWrong_answers() < MAX_ERRORS) {

						correct = game.questionsList.get(
								game.getCurr_question_cnt())
								.getCorrect_answer();
						if (answerButton.getText().equals(correct)) { // right
																		// answer
							game.setScore(game.getScore() + 3);
							lastAnswer = "Correct!";
							game.setCorrect_answer(game.getCorrect_answer() + 1);
						} else { // wrong answer
							lastAnswer = "Wrong...";
							game.setScore(game.getScore() - 1);
							game.setWrong_answers(game.getWrong_answers() + 1);
						}

						game.setCurr_question_cnt(game.getCurr_question_cnt() + 1);
						scoreLabel.setText(Integer.toString(game.getScore()));
						if (game.getWrong_answers() >= MAX_ERRORS
								|| game.getCurr_question_cnt() == game
										.getQuestions_cnt()) {
							if (game.getWrong_answers() >= MAX_ERRORS) { // game
																			// over
																			// MAX_ERROR
								String gameOverMessage = "Your final score is "
										+ game.getScore()
										+ " after "
										+ (game.getCorrect_answer() + game
												.getWrong_answers())
										+ " questions.";
								GUIUtils.showInfoDialog(shell, "GAME OVER",
										gameOverMessage);
							}

						}
						nextQuestion(game);
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {

				}
			});
		}

	}

	private void nextQuestion(Game game) {
		if (game.getCurr_question_cnt() == game.getQuestions_cnt()) { // no more
																		// questions
			String gameOverMessage = "Your final score is " + game.getScore()
					+ " after "
					+ (game.getCorrect_answer() + game.getWrong_answers())
					+ " questions.";
			GUIUtils.showInfoDialog(shell, "GAME OVER", gameOverMessage);

		} else { // update next question
			if (game.getWrong_answers() < MAX_ERRORS) {
				updateQuestionPanel(game,
						game.questionsList.get(game.getCurr_question_cnt())
								.getQuestion(),
						game.questionsList.get(game.getCurr_question_cnt())
								.getAnswers());
			}
			gameProccess(game);

		}
	}

	/**
	 * Creates the panel that displays the current score
	 */
	private void createScorePanel() {
		Composite scorePanel = new Composite(shell, SWT.BORDER);
		scorePanel.setLayoutData(GUIUtils.createFillGridData(1));
		scorePanel.setLayout(new GridLayout(2, false));

		final Label label = new Label(scorePanel, SWT.NONE);
		label.setText("Total score: ");

		// The label which displays the score; initially empty
		scoreLabel = new Label(scorePanel, SWT.NONE);
		scoreLabel.setLayoutData(GUIUtils.createFillGridData(1));
	}

	/**
	 * Creates the panel that displays the questions, as soon as the game
	 * starts. See the updateQuestionPanel for creating the question and answer
	 * buttons
	 */
	private void createQuestionPanel() {
		questionPanel = new Composite(shell, SWT.BORDER);
		questionPanel.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
				true, true));
		questionPanel.setLayout(new GridLayout(2, true));

		// Initially, only displays a message
		Label label = new Label(questionPanel, SWT.NONE);
		label.setText("No question to display, yet.");
		label.setLayoutData(GUIUtils.createFillGridData(2));
	}

	/**
	 * Serves to display the question and answer buttons
	 * 
	 */
	private void updateQuestionPanel(Game game, String question,
			List<String> answers) {

		// clear the question panel
		Control[] children = questionPanel.getChildren();
		for (Control control : children) {
			control.dispose();
		}

		// create the instruction label
		Label instructionLabel = new Label(questionPanel, SWT.CENTER | SWT.WRAP);
		instructionLabel.setText(lastAnswer + "Answer the following question:");
		instructionLabel.setLayoutData(GUIUtils.createFillGridData(2));

		// create the question label
		Label questionLabel = new Label(questionPanel, SWT.CENTER | SWT.WRAP);
		questionLabel.setText(question);
		questionLabel.setFont(boldFont);
		questionLabel.setLayoutData(GUIUtils.createFillGridData(2));

		// create the answer buttons
		buttons_array = new Button[4];
		for (int i = 0; i < 4; i++) {
			buttons_array[i] = new Button(questionPanel, SWT.PUSH | SWT.WRAP);
			buttons_array[i].setText(answers.get(i));
			GridData answerLayoutData = GUIUtils.createFillGridData(1);
			answerLayoutData.verticalAlignment = SWT.FILL;
			buttons_array[i].setLayoutData(answerLayoutData);
		}

		// create the "Pass" button to skip a question + listener
		passButton = new Button(questionPanel, SWT.PUSH);
		passButton.setText("Pass");
		GridData data = new GridData(GridData.CENTER, GridData.CENTER, true,
				false);
		data.horizontalSpan = 2;
		passButton.setLayoutData(data);
		passButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.getSource() instanceof Button
						&& game.getWrong_answers() < MAX_ERRORS) {

					game.setCurr_question_cnt(game.getCurr_question_cnt() + 1);
					lastAnswer = "";
					if (game.getCurr_question_cnt() <= game.getQuestions_cnt())
						nextQuestion(game);
					else
						gameProccess(game);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		// two operations to make the new widgets display properly
		questionPanel.pack();
		questionPanel.getParent().layout();
	}

	/**
	 * Opens the main window and executes the event loop of the application
	 */
	private void runApplication() {
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		boldFont.dispose();
	}

}
